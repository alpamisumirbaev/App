package com.example.model

data class Question(
    val id: Int,
    val blockId: Int,
    val category: String,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class QuestionBlock(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val startQuestionId: Int,
    val endQuestionId: Int,
    val questionCount: Int = 25
)
