package com.example.quiz

import androidx.lifecycle.ViewModel
import com.example.quiz.db.Question

class QuestionFragmentViewModel : ViewModel(){

    var id = 0
    var position = 0
    var wrongAnswer = 0
    var correctAnswer = 0

    var listOfYourAnswers = arrayListOf<String>();
    var questionList = arrayListOf<Question>();

    fun updateQuestionPosition(){
        position++
    }
    fun updateCorrectAnswer(){
        correctAnswer++
    }

    fun updateWrongAnswer(){
        wrongAnswer++
    }

    fun updateCurrentQuestionAnswer(yourAnswer: String){
        listOfYourAnswers.add(yourAnswer);
    }
    fun addQuestionList(questions: List<Question>){
        questionList.addAll(questions);
    }

}