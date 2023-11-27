package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduleapp.databinding.FragmentCalendarBinding

class calendarFragment : Fragment() {
    private lateinit var todoAdapter: TodoAdapter
    private val todoList= mutableListOf<TodoList>()
    var binding: FragmentCalendarBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCalendarBinding.inflate(inflater)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoAdapter=TodoAdapter(todoList)
        binding?.recTodo?.layoutManager=LinearLayoutManager(context)
        binding?.recTodo?.adapter=todoAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}