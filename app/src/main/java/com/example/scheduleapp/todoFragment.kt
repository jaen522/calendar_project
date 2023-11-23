package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentTodoBinding

class todoFragment : Fragment() {
    private var binding:FragmentTodoBinding?=null
    private var t_date:String?=null
    private var t_name:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        t_date=arguments?.getString("23년11월23일")
        t_name=arguments?.getString("객체지향프로그래밍 과제")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTodoBinding.inflate(inflater)
        return binding?.root
    }
    //createbutton 누르면 main으로 이동
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.finish_todo)?.setOnClickListener {
            findNavController().navigate(R.id.action_todoFragment_to_calendarFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}