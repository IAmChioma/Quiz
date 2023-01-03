package com.example.quiz.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities= [Question::class],
    version = 1)

abstract class QuestionDatabase():RoomDatabase() {
    abstract fun getQuestionDao(): QuestionDao

    companion object {
        @Volatile private  var instance: QuestionDatabase? = null;
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, QuestionDatabase::class.java, "questionDatabase"
        ).build()
    }
}