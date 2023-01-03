package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.quiz.db.Question
import com.example.quiz.db.QuestionDatabase
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question_list.*
import kotlinx.coroutines.launch


class QuestionListFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_list, container, false)
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        recycler_question_list.layoutManager  = LinearLayoutManager(context)

        launch {
            context?.let {
                val questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
                recycler_question_list.adapter = QuestionsListAdapter(it,questions)



            }
        }

        button_add.setOnClickListener(){
            val action = QuestionListFragmentDirections.actionQuestionListFragmentToAddQuestionFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}