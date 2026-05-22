package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuestionDataSource
import com.example.data.local.ProgressRepository
import com.example.data.local.QuizHistory
import com.example.model.Question
import com.example.model.QuestionBlock
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
    Home,
    Quiz,
    Results
}

class QuizViewModel(private val repository: ProgressRepository) : ViewModel() {

    // Current navigation screen inside our modular state machine
    private val _currentScreen = MutableStateFlow(AppScreen.Home)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Active block being practiced (1..12, or -1 for the Random practice block)
    private val _currentBlockId = MutableStateFlow<Int?>(null)
    val currentBlockId: StateFlow<Int?> = _currentBlockId.asStateFlow()

    // 25 active questions for the ongoing session
    private val _quizQuestions = MutableStateFlow<List<Question>>(emptyList())
    val quizQuestions: StateFlow<List<Question>> = _quizQuestions.asStateFlow()

    // Active question index inside the session (0..24)
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    // Selections made in the current session (Question ID -> Selected Option Index)
    private val _sessionSelections = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val sessionSelections: StateFlow<Map<Int, Int>> = _sessionSelections.asStateFlow()

    // Observe persistent questions completed from the Room Database
    val persistentProgress = repository.allProgress.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Observe test completion logs
    val testHistory = repository.allHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Derived: UI state reflecting progress for each standard block
    val blockProgressList: StateFlow<List<BlockProgressState>> = persistentProgress.map { progressList ->
        val progressMap = progressList.associateBy { it.questionId }
        QuestionDataSource.blocks.map { block ->
            var answeredCount = 0
            var correctCount = 0
            for (qId in block.startQuestionId..block.endQuestionId) {
                val match = progressMap[qId]
                if (match != null) {
                    answeredCount++
                    if (match.isCorrect) {
                        correctCount++
                    }
                }
            }
            BlockProgressState(
                block = block,
                answeredCount = answeredCount,
                correctCount = correctCount,
                percentCompleted = (answeredCount * 100) / block.questionCount
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Derived: overall summary progress metrics
    val overallProgress: StateFlow<OverallProgressMetrics> = persistentProgress.map { progressList ->
        val totalQuestionsCount = 300
        val answeredCount = progressList.size
        val correctCount = progressList.count { it.isCorrect }
        val completionPercentage = if (totalQuestionsCount > 0) (answeredCount * 100) / totalQuestionsCount else 0
        OverallProgressMetrics(
            totalQuestions = totalQuestionsCount,
            totalAnswered = answeredCount,
            totalCorrect = correctCount,
            percentCompleted = completionPercentage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OverallProgressMetrics(300, 0, 0, 0)
    )

    // Start a standard block quiz (1..12)
    fun startBlockQuiz(blockId: Int) {
        val questions = QuestionDataSource.getQuestionsForBlock(blockId)
        _currentBlockId.value = blockId
        _quizQuestions.value = questions
        _currentQuestionIndex.value = 0
        _sessionSelections.value = emptyMap()
        _currentScreen.value = AppScreen.Quiz
    }

    // Start a random practice block (pulling 25 random questions from all 300)
    fun startRandomQuiz() {
        val questions = QuestionDataSource.getRandomQuestions(25)
        _currentBlockId.value = -1 // representing "Random Practice"
        _quizQuestions.value = questions
        _currentQuestionIndex.value = 0
        _sessionSelections.value = emptyMap()
        _currentScreen.value = AppScreen.Quiz
    }

    // Submit an answer for the active question
    fun selectOption(questionId: Int, selectedIndex: Int) {
        // Prevent re-answering a question already locked in this session
        if (_sessionSelections.value.containsKey(questionId)) return

        val currentQuestions = _quizQuestions.value
        val question = currentQuestions.firstOrNull { it.id == questionId } ?: return
        val isCorrect = question.correctOptionIndex == selectedIndex

        // Lock in the choice in-memory for the current session review
        val updatedSelections = _sessionSelections.value.toMutableMap()
        updatedSelections[questionId] = selectedIndex
        _sessionSelections.value = updatedSelections

        // Log to persistent Room database immediately so state is saved
        viewModelScope.launch {
            repository.saveProgress(questionId, selectedIndex, isCorrect)
        }
    }

    // Navigate to next question, or submit the quiz if reaching the 25th question
    fun navigateNext() {
        val currentIndex = _currentQuestionIndex.value
        if (currentIndex < 24) {
            _currentQuestionIndex.value = currentIndex + 1
        } else {
            submitQuizResults()
        }
    }

    // Navigate to previous question (for review)
    fun navigatePrevious() {
        val currentIndex = _currentQuestionIndex.value
        if (currentIndex > 0) {
            _currentQuestionIndex.value = currentIndex - 1
        }
    }

    // Complete the quiz session, tally the score, log history, and change to ResultsScreen
    private fun submitQuizResults() {
        val currentQuestions = _quizQuestions.value
        val selections = _sessionSelections.value
        var correctCount = 0

        currentQuestions.forEach { q ->
            val sel = selections[q.id]
            if (sel != null && sel == q.correctOptionIndex) {
                correctCount++
            }
        }

        val blockName = when (val blockId = _currentBlockId.value) {
            -1 -> "Random Test Block"
            null -> "Review Block"
            else -> {
                val b = QuestionDataSource.blocks.firstOrNull { it.id == blockId }
                b?.name ?: "Practice Block #$blockId"
            }
        }

        viewModelScope.launch {
            repository.logHistory(blockName, correctCount, 25)
        }

        _currentScreen.value = AppScreen.Results
    }

    // Go back to the home dashboard
    fun navigateToHome() {
        _currentScreen.value = AppScreen.Home
        _currentBlockId.value = null
        _quizQuestions.value = emptyList()
    }

    // Reset all standard progress data in database
    fun clearAllProgress() {
        viewModelScope.launch {
            repository.clearProgress()
            repository.clearHistory()
        }
        _sessionSelections.value = emptyMap()
    }
}

data class BlockProgressState(
    val block: QuestionBlock,
    val answeredCount: Int,
    val correctCount: Int,
    val percentCompleted: Int
)

data class OverallProgressMetrics(
    val totalQuestions: Int,
    val totalAnswered: Int,
    val totalCorrect: Int,
    val percentCompleted: Int
)
