package com.example.quiz.db

import androidx.room.*

@Dao
interface QuestionDao {
    @Insert
    suspend fun addQuestion(question:Question)
    @Query("SELECT * FROM QUESTION ORDER BY id DESC")
    suspend fun getAllQuestions():List<Question>
    @Insert
    suspend fun addMultipleQuestions(vararg question: Question)
    @Update
    suspend fun updateQuestion(question:Question)
    @Delete
    suspend fun deleteQuestion(question: Question)
}