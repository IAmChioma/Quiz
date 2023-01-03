package com.example.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.db.Question
import kotlinx.android.synthetic.main.result_analysis_layout.view.*

class ResultAnalysisAdapter(val questions: List<Question>, val answers: List<String>): RecyclerView.Adapter<ResultAnalysisAdapter.ResultListViewHolder>() {
    class ResultListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultListViewHolder {
        return ResultListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.result_analysis_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultListViewHolder, position: Int) {
        holder.view.the_question.text = questions[position].title
        holder.view.correct_answer_text.text = questions[position].correctAnswer
        holder.view.your_answer_text.text = answers[position]


    }

    override fun getItemCount() = questions.size
}