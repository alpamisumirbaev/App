package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
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
import com.example.data.QuestionDataSource
import com.example.model.Question
import com.example.ui.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val quizQuestions by viewModel.quizQuestions.collectAsStateWithLifecycle()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsStateWithLifecycle()
    val sessionSelections by viewModel.sessionSelections.collectAsStateWithLifecycle()
    val blockId by viewModel.currentBlockId.collectAsStateWithLifecycle()

    if (quizQuestions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val currentQuestion = quizQuestions[currentQuestionIndex]
    val selectedOptionIndex = sessionSelections[currentQuestion.id]
    val isAnswered = selectedOptionIndex != null

    val blockName = remember(blockId) {
        if (blockId == -1) {
            "Random Practice Quiz"
        } else {
            QuestionDataSource.blocks.firstOrNull { it.id == blockId }?.name ?: "Practice Block"
        }
    }

    val scrollState = rememberScrollState()

    // Scroll to top whenever we change questions so that content is fully visible
    LaunchedEffect(currentQuestionIndex) {
        scrollState.animateScrollTo(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = blockName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                        Text(
                            text = "${currentQuestion.category} Block",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateToHome() },
                        modifier = Modifier.testTag("quiz_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cancel and return to dashboard"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            // Persistent navigation bar for Forward/Backward actions
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Question review button
                    TextButton(
                        onClick = { viewModel.navigatePrevious() },
                        enabled = currentQuestionIndex > 0,
                        modifier = Modifier.testTag("prev_question_button")
                    ) {
                        Text("PREVIOUS")
                    }

                    // Progress Text
                    Text(
                        text = "${currentQuestionIndex + 1} of 25",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Next/Finish button (requires answer locked in)
                    Button(
                        onClick = { viewModel.navigateNext() },
                        enabled = isAnswered,
                        modifier = Modifier.testTag("next_question_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        val isLast = currentQuestionIndex == 24
                        Text(text = if (isLast) "SUBMIT TEST" else "NEXT QUESTION")
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Visual dynamic progress bar
            LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1) / 25f },
                modifier = Modifier.fillMaxWidth().height(4.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Interactive Question card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "QUESTION ${currentQuestion.id}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = "Topic: ${currentQuestion.category}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentQuestion.questionText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 24.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Text(
                    text = "Select one answer:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )

                // Render matching multiple choice elements
                currentQuestion.options.forEachIndexed { idx, optionText ->
                    val optionLetter = when (idx) {
                        0 -> "A"
                        1 -> "B"
                        2 -> "C"
                        3 -> "D"
                        else -> "?"
                    }

                    OptionItem(
                        letter = optionLetter,
                        text = optionText,
                        isSelected = selectedOptionIndex == idx,
                        isCorrectChoice = currentQuestion.correctOptionIndex == idx,
                        isAnswered = isAnswered,
                        onClick = { viewModel.selectOption(currentQuestion.id, idx) }
                    )
                }

                // Tutoring Explanation sheet with smooth fade animation
                AnimatedVisibility(
                    visible = isAnswered,
                    enter = fadeIn(animationSpec = spring()) + expandVertically(animationSpec = spring()),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    val wasCorrect = selectedOptionIndex == currentQuestion.correctOptionIndex

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (wasCorrect) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                            } else {
                                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f)
                            }
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (wasCorrect) {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            } else {
                                MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("explanation_card")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Review explanation icon",
                                    tint = if (wasCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (wasCorrect) "Excellent! Correct Explanation" else "Learning Moment: Details",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (wasCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = currentQuestion.explanation,
                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptionItem(
    letter: String,
    text: String,
    isSelected: Boolean,
    isCorrectChoice: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    // Dynamic styling reflecting correctness state
    val containerColor = when {
        isAnswered && isCorrectChoice -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        isAnswered && isSelected && !isCorrectChoice -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f)
        else -> MaterialTheme.colorScheme.surface
    }

    val borderStroke = when {
        isAnswered && isCorrectChoice -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        isAnswered && isSelected && !isCorrectChoice -> BorderStroke(2.dp, MaterialTheme.colorScheme.error)
        else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
    }

    val iconColor = when {
        isCorrectChoice -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isAnswered, onClick = onClick)
            .testTag("option_card_${letter}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = borderStroke,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Letter badge shape
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        color = when {
                            isAnswered && isCorrectChoice -> MaterialTheme.colorScheme.primary
                            isAnswered && isSelected && !isCorrectChoice -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.secondaryContainer
                        }
                    )
            ) {
                Text(
                    text = letter,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = when {
                        isAnswered && isCorrectChoice -> Color.White
                        isAnswered && isSelected && !isCorrectChoice -> Color.White
                        else -> MaterialTheme.colorScheme.onSecondaryContainer
                    }
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Option details Text
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            // Correctness visual feedback icons
            if (isAnswered) {
                if (isCorrectChoice) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Correct indicator",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                } else if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Incorrect indicator",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
