package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
    super.onViewCreated(view,savedInstanceState)
        btnStart.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment()
            findNavController(it).navigate(action)
        }
        btnViewQuestionList.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToQuestionListFragment()
            findNavController(it).navigate(action)
        }
    }
}