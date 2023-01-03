package com.example.quiz.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Question(

    val title :String,
    val optionOne :String,
    val optionTwo :String,
    val optionThree :String,
    val correctAnswer :String,
): Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}