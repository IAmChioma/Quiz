package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.example.quiz.db.Question
import com.example.quiz.db.QuestionDatabase
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.launch


class QuestionFragment : BaseFragment() {
    lateinit var questions: List<Question>
    private lateinit var questionViewModel: QuestionFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionViewModel = ViewModelProvider(this).get(QuestionFragmentViewModel::class.java)

        launch {
            context?.let {
                questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
                questionViewModel.addQuestionList(questions)
                populateFirstQuestion()
            }
        }


        btnNext.setOnClickListener { goToNext(it) }
        btnHome.setOnClickListener { goToHome(it) }

    }


    private fun goToNext(view: View) {
        // Check if user selected an answer
        if(radioGroup.checkedRadioButtonId == -1){
            Toast.makeText(context, "Kindly choose an answer from the options", Toast.LENGTH_LONG)
                .show()
            return
        }

        val clicked = radioGroup.findViewById(radioGroup.checkedRadioButtonId) as RadioButton
        val selectedValue = clicked.text.toString()

        radioGroup.clearCheck()
        if (questions[questionViewModel.position].correctAnswer == selectedValue) {
            questionViewModel.updateCorrectAnswer()
        } else {
            questionViewModel.updateWrongAnswer()
        }
        questionViewModel.updateCurrentQuestionAnswer(selectedValue)

        Toast.makeText(
            context,
            "You selected $selectedValue",
            Toast.LENGTH_SHORT
        ).show()


        if (questionViewModel.position < questionViewModel.questionList.size-1) {
            questionViewModel.updateQuestionPosition()
            loadNewQuestion()

        }
        else {
            //Need to pass the summary data including the correct answer to the Summary Fragment
            val action = QuestionFragmentDirections.actionQuestionFragmentToSummaryFragment()
            action.correctAnswer = questionViewModel.correctAnswer
            action.answersList = questionViewModel.listOfYourAnswers.toTypedArray()
            action.wrongAnswer = questionViewModel.wrongAnswer
            action.total = questionViewModel.questionList.size

            setToDefault()
            findNavController(view).navigate(action)
        }
    }

    private fun setToDefault() {
        questionViewModel.questionList.clear()
        questionViewModel.listOfYourAnswers.clear()
        questionViewModel.wrongAnswer = 0
        questionViewModel.correctAnswer = 0
        questionViewModel.position = 0
    }


    private fun populateFirstQuestion() {
        if(questionViewModel.questionList.isEmpty()) { disableItems() }
        else { loadNewQuestion() }
    }

    private fun disableItems() {
        radioGroup.visibility = View.INVISIBLE
        text_question.text = buildString { append("No questions Found, Kindly populate questions") }
        Toast.makeText(context, "No questions Found, Kindly populate questions", Toast.LENGTH_LONG)
            .show()
        btnNext.isEnabled = false
    }

    private fun loadNewQuestion(){
        text_question.text = questionViewModel.questionList[questionViewModel.position].title
        answer_one.text = questionViewModel.questionList[questionViewModel.position].optionOne
        answer_two.text = questionViewModel.questionList[questionViewModel.position].optionTwo
        answer_three.text = questionViewModel.questionList[questionViewModel.position].optionThree

    }
    private fun goToHome(it: View){
        val action = QuestionFragmentDirections.actionQuestionFragmentToHomeFragment()
        findNavController(it).navigate(action)
    }
}