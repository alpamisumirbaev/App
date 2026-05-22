package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Query("SELECT * FROM question_progress")
    fun getAllProgressFlow(): Flow<List<QuestionProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: QuestionProgress)

    @Query("DELETE FROM question_progress")
    suspend fun clearAllProgress()

    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    fun getAllHistoryFlow(): Flow<List<QuizHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: QuizHistory)

    @Query("DELETE FROM quiz_history")
    suspend fun clearAllHistory()
}
