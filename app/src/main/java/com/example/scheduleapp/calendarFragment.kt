package com.example.scheduleapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduleapp.databinding.FragmentCalendarBinding
import com.example.scheduleapp.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class calendarFragment : Fragment() {
    private lateinit var todoAdapter: TodoAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(TodoViewModel::class.java) }
    private var binding: FragmentCalendarBinding?=null
    private var selectedDate: String?=null

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
        binding?.calendar?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            try {
                selectedDate = "$year/${month + 1}/$dayOfMonth"
                fetchDate(selectedDate.orEmpty())
            } catch (e: Exception) {
                Log.e("CalendarFragment", "Error handling date change", e)
            }
        }
        todoAdapter.setItemCheckBoxClickListener(object : TodoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todoList = viewModel.fetchDate(selectedDate.orEmpty()).value ?: return@launch
                    if (position < todoList.size) {
                        val updatedTodo =
                            todoList[position].copy(isChecked = !todoList[position].isChecked)
                        viewModel.update(updatedTodo)
                    }
                }
            }
        })
    }
    private fun fetchDate(date: String) {
        if (date.isNotEmpty()) {
            viewModel.fetchDate(date).observe(viewLifecycleOwner) { todoList ->
                todoAdapter.setListData(todoList)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}