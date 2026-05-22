package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_progress")
data class QuestionProgress(
    @PrimaryKey val questionId: Int,
    val selectedOptionIndex: Int,
    val isCorrect: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
