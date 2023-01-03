package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.quiz.db.Question
import com.example.quiz.db.QuestionDatabase
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.android.synthetic.main.result_analysis_layout.*
import kotlinx.coroutines.launch


class SummaryFragment : BaseFragment() {
    var answerList: List<String> = arrayListOf();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            answerList = SummaryFragmentArgs.fromBundle(it).answersList!!.toList()
            correct_question_count.text =
                SummaryFragmentArgs.fromBundle(it).correctAnswer.toString()
            wrong_answer_count.text =
                SummaryFragmentArgs.fromBundle(it).wrongAnswer.toString()
            total_question_count.text =
                SummaryFragmentArgs.fromBundle(it).total.toString()
            your_score_count.text = buildString {
                append(SummaryFragmentArgs.fromBundle(it).correctAnswer)
                append(" / ").append(SummaryFragmentArgs.fromBundle(it).total.toString())
            }
        }
        btnResultAnalysis.setOnClickListener{gotoResultAnalysis(it)}
        btnTryAgain.setOnClickListener{tryAgain(it)}

    }
    fun tryAgain(it: View) {
        val action = SummaryFragmentDirections.actionSummaryFragmentToHomeFragment()
        Navigation.findNavController(it).navigate(action)
    }

    fun gotoResultAnalysis(it: View) {
        val action = SummaryFragmentDirections.actionSummaryFragmentToResultFragment()
        action.answerResultList = answerList.toTypedArray()
        Navigation.findNavController(it).navigate(action)
    }

}
