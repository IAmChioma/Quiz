package com.example.quiz

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.quiz.db.Question
import com.example.quiz.db.QuestionDatabase
import kotlinx.android.synthetic.main.fragment_add_question.*
import kotlinx.android.synthetic.main.question_list_layout.*
import kotlinx.android.synthetic.main.result_analysis_layout.*
import kotlinx.coroutines.launch


class AddQuestionFragment : BaseFragment() {
    private var question: Question? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_question, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        arguments?.let{
            question = AddQuestionFragmentArgs.fromBundle(it).question
            print(question)
            title.setText(question?.title)
            edit_option_one.setText(question?.optionOne)
            edit_option_two.setText(question?.optionTwo)
            edit_option_three.setText(question?.optionThree)
            edit_correct_answer.setText(question?.correctAnswer)
        }

        button_delete.setOnClickListener{
            if( question != null ) deleteQuestion() else context?.toast("Cannot Delete")
        }
        button_save.setOnClickListener {
            // Retrieve the values from the EditText fields
            val questionTitle = title.text.toString()
            val optionOne = edit_option_one.text.toString()
            val optionTwo = edit_option_two.text.toString()
            val optionThree = edit_option_three.text.toString()
            val correctAnswer = edit_correct_answer.text.toString()

            // Check the input values are empty, then set the error message and give the focus

            if(questionTitle.isEmpty()){
                title.error = "Title Required"
                title.requestFocus()
                return@setOnClickListener // stop further execution ie returning at the end of the setOnClickListener
            }
            if(optionOne.isEmpty()){
                edit_option_one.error = "Option one is Required"
                edit_option_one.requestFocus()
                return@setOnClickListener // stop further execution ie returning at the end of the setOnClickListener
            }
            if(optionTwo.isEmpty()){
                edit_option_two.error = "Option two is Required"
                edit_option_two.requestFocus()
                return@setOnClickListener // stop further execution ie returning at the end of the setOnClickListener
            }
            if(optionThree.isEmpty()){
                edit_option_three.error = "Option three is  Required"
                edit_option_three.requestFocus()
                return@setOnClickListener // stop further execution ie returning at the end of the setOnClickListener
            }
            if(correctAnswer.isEmpty()){
                correct_answer.error = "Correct Answer Required"
                correct_answer.requestFocus()
                return@setOnClickListener // stop further execution ie returning at the end of the setOnClickListener
            }

            launch {
                context?.let {
                    val mQuestion = Question(questionTitle,optionOne,optionTwo,optionThree,correctAnswer)
                    // question == null means Inserting a new Note
                    if (question == null) {
                        QuestionDatabase(it).getQuestionDao().addQuestion(mQuestion)
                        it.toast("Question Saved")
                    } else {
                        // Update the question
                        mQuestion.id = question!!.id
                        QuestionDatabase(it).getQuestionDao().updateQuestion(mQuestion)
                        it.toast("Question Updated")
                    }
                    // after adding a question need to return to Home_Fragment as per the navigation directions
                    val action = AddQuestionFragmentDirections.actionAddQuestionFragmentToQuestionListFragment()
                    Navigation.findNavController(view).navigate(action)

                }
            }

        }
    }
    private fun deleteQuestion(){
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {dialog, which ->
                launch {
                    QuestionDatabase(context).getQuestionDao().deleteQuestion(question!!)
                    val action = AddQuestionFragmentDirections.actionAddQuestionFragmentToQuestionListFragment()
                    view?.let { Navigation.findNavController(it).navigate(action) }
                }
            }
            setNegativeButton("No") {dialog, which->

            }
        }.create().show()
    }
}