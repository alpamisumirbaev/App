package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Question
import com.example.ui.viewmodel.QuizViewModel

@Composable
fun ResultsScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val quizQuestions by viewModel.quizQuestions.collectAsStateWithLifecycle()
    val sessionSelections by viewModel.sessionSelections.collectAsStateWithLifecycle()
    val blockId by viewModel.currentBlockId.collectAsStateWithLifecycle()

    // Calculate details
    val incorrectQuestionsWithAnswers = remember(quizQuestions, sessionSelections) {
        quizQuestions.mapNotNull { q ->
            val selectedIdx = sessionSelections[q.id]
            val isCorrect = selectedIdx != null && selectedIdx == q.correctOptionIndex
            if (selectedIdx != null && !isCorrect) {
                IncorrectAnswerDetail(
                    question = q,
                    selectedOptionIndex = selectedIdx
                )
            } else {
                null
            }
        }
    }

    val totalQuestionsCount = quizQuestions.size
    val correctCount = totalQuestionsCount - incorrectQuestionsWithAnswers.size
    val accuracyPercent = if (totalQuestionsCount > 0) (correctCount * 100) / totalQuestionsCount else 0

    val (badgeText, badgeColor) = when {
        accuracyPercent == 100 -> "Perfect Mastery! 🏆" to MaterialTheme.colorScheme.primary
        accuracyPercent >= 80 -> "Superb Performance! 🌟" to MaterialTheme.colorScheme.primary
        accuracyPercent >= 60 -> "Good Job, Keep It Up! 👍" to MaterialTheme.colorScheme.secondary
        else -> "Keep Practicing, You Got This! 💪" to MaterialTheme.colorScheme.error
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tally Card at top
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("results_summary_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Practice Completed",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Huge circular accuracy value
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(120.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { accuracyPercent / 100f },
                            modifier = Modifier.fillMaxSize(),
                            strokeWidth = 10.dp,
                            color = badgeColor,
                            trackColor = badgeColor.copy(alpha = 0.15f),
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$accuracyPercent%",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                                color = badgeColor
                            )
                            Text(
                                text = "Score",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Feedback badge
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = badgeColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Correct: $correctCount / $totalQuestionsCount questions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Action controls
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Secondary Restart block button
                OutlinedButton(
                    onClick = {
                        val currentBlockId = blockId
                        if (currentBlockId == -1) {
                            viewModel.startRandomQuiz()
                        } else if (currentBlockId != null) {
                            viewModel.startBlockQuiz(currentBlockId)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("restart_block_button"),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Restart")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("RETAKE RUN")
                }

                // Primary Return home button
                Button(
                    onClick = { viewModel.navigateToHome() },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("return_home_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("MAIN BOARD")
                }
            }
        }

        // Review list header
        item {
            val titleText = if (incorrectQuestionsWithAnswers.isEmpty()) {
                "Perfect Score! No mistakes to review."
            } else {
                "Review ${incorrectQuestionsWithAnswers.size} Incorrect Answers"
            }

            Text(
                text = titleText,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp, start = 4.dp)
            )
        }

        // Scrollable list of explanation cards for incorrectly answered questions
        if (incorrectQuestionsWithAnswers.isNotEmpty()) {
            items(incorrectQuestionsWithAnswers) { itemDetail ->
                IncorrectAnswerCard(detail = itemDetail)
            }
        } else {
            // Display empty state for 100% score
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "No mistakes",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Absolute Perfection!",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "You answered every single topic correctly in this block. Keep carrying this incredible focus forward!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IncorrectAnswerCard(detail: IncorrectAnswerDetail) {
    val q = detail.question

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Question tag line
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Q-${q.id} (Block ${q.blockId})",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = q.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Question details
            Text(
                text = q.questionText,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Answers selected / correct breakdown
            val selectedOptionLetter = when (detail.selectedOptionIndex) {
                0 -> "A"
                1 -> "B"
                2 -> "C"
                3 -> "D"
                else -> "?"
            }
            val correctOptionLetter = when (q.correctOptionIndex) {
                0 -> "A"
                1 -> "B"
                2 -> "C"
                3 -> "D"
                else -> "?"
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                // Your answer (Incorrect)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.error)
                    ) {
                        Text(
                            text = selectedOptionLetter,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your Answer: ${q.options.getOrNull(detail.selectedOptionIndex) ?: ""}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Correct answer
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = correctOptionLetter,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Correct Answer: ${q.options.getOrNull(q.correctOptionIndex) ?: ""}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explanation drawer (Visible directly)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
                    .padding(12.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Intellect Explanation tip",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Explanation:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = q.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

data class IncorrectAnswerDetail(
    val question: Question,
    val selectedOptionIndex: Int
)
