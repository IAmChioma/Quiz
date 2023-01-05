package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.db.Question
import com.example.quiz.db.QuestionDatabase
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.coroutines.launch


class ResultFragment : BaseFragment() {
    lateinit var questions: List<Question>
    var answerList: List<String> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_result.layoutManager  = LinearLayoutManager(context)

        arguments?.let {
            answerList = ResultFragmentArgs.fromBundle(it).answerResultList!!.toList()

        }
        launch {
            context?.let {
                questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
                recycler_view_result.adapter = ResultAnalysisAdapter(questions, answerList)

            }
        }

    }

}