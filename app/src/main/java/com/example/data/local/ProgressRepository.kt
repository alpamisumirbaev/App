package com.example.data.local

import kotlinx.coroutines.flow.Flow

class ProgressRepository(private val progressDao: ProgressDao) {

    val allProgress: Flow<List<QuestionProgress>> = progressDao.getAllProgressFlow()

    val allHistory: Flow<List<QuizHistory>> = progressDao.getAllHistoryFlow()

    suspend fun saveProgress(questionId: Int, selectedOptionIndex: Int, isCorrect: Boolean) {
        val progress = QuestionProgress(
            questionId = questionId,
            selectedOptionIndex = selectedOptionIndex,
            isCorrect = isCorrect,
            timestamp = System.currentTimeMillis()
        )
        progressDao.insertProgress(progress)
    }

    suspend fun clearProgress() {
        progressDao.clearAllProgress()
    }

    suspend fun logHistory(blockName: String, score: Int, totalQuestions: Int) {
        val history = QuizHistory(
            blockName = blockName,
            score = score,
            totalQuestions = totalQuestions,
            timestamp = System.currentTimeMillis()
        )
        progressDao.insertHistory(history)
    }

    suspend fun clearHistory() {
        progressDao.clearAllHistory()
    }
}
