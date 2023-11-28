package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduleapp.databinding.FragmentCalendarBinding
import com.example.scheduleapp.viewmodel.TodoViewModel

class calendarFragment : Fragment() {
    private lateinit var todoAdapter: TodoAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(TodoViewModel::class.java) }
    private var binding: FragmentCalendarBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCalendarBinding.inflate(inflater)
        todoAdapter=TodoAdapter(requireContext())
        binding?.recTodo?.layoutManager=LinearLayoutManager(context)
        binding?.recTodo?.adapter=todoAdapter
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }
    fun observeData(){
        viewModel.fetchDate().observe(viewLifecycleOwner,Observer{
            todoAdapter.setListData(it)
            todoAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}