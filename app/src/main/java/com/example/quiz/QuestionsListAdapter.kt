package com.example.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.db.Question
import kotlinx.android.synthetic.main.question_list_layout.view.*

class QuestionsListAdapter(var context: Context?, val questions: List<Question>): RecyclerView.Adapter<QuestionsListAdapter.QuestionsListViewHolder>() {
    class QuestionsListViewHolder(val view: View): RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsListViewHolder {
        return QuestionsListViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.question_list_layout,parent,false))
    }

    override fun onBindViewHolder(holder: QuestionsListViewHolder, position: Int) {
        holder.view.question_title.text = questions[position].title
        holder.view.setOnClickListener{
            val action = QuestionListFragmentDirections.actionQuestionListFragmentToAddQuestionFragment()
            action.question = questions[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount() = questions.size
}