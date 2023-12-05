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
import com.example.scheduleapp.viewmodel.AccountViewModel
import com.example.scheduleapp.viewmodel.ScheduleViewModel
import com.example.scheduleapp.viewmodel.TodoViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class calendarFragment : Fragment() {
    private var binding: FragmentCalendarBinding?=null
    private val scheduleViewModel by lazy { ViewModelProvider(this).get(ScheduleViewModel::class.java) }
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var todoAdapter: TodoAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(TodoViewModel::class.java) }
    private var selectedDate: String?=null
    private lateinit var accountAdapter: accountAdapter
    private val accountViewModel by lazy { ViewModelProvider(this).get(AccountViewModel::class.java) }
    private lateinit var todoRef: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCalendarBinding.inflate(inflater)
        scheduleAdapter = ScheduleAdapter(requireContext())
        binding?.recSchedule?.layoutManager = LinearLayoutManager(context)
        binding?.recSchedule?.adapter = scheduleAdapter
        todoAdapter=TodoAdapter(requireContext())
        binding?.recTodo?.layoutManager=LinearLayoutManager(context)
        binding?.recTodo?.adapter=todoAdapter
        accountAdapter = accountAdapter(requireContext())
        binding?.recAccount?.layoutManager = LinearLayoutManager(context)
        binding?.recAccount?.adapter = accountAdapter
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.calendar?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            try {
                selectedDate = "$year/${month + 1}/$dayOfMonth"
                fetchSchedule(selectedDate.orEmpty())
                fetchDate(selectedDate.orEmpty())
                fetchAccount(selectedDate.orEmpty())
            } catch (e: Exception) {
            }
        }
        todoRef = Firebase.database.reference.child("todo")
        todoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList = mutableListOf<TodoList>()
                for (data in snapshot.children) {
                    val item = data.getValue(TodoList::class.java)
                    item?.let {
                        todoList.add(it)
                    }
                }
                todoAdapter.setListData(todoList)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("CalendarFragment", "Error reading data", error.toException())
            }
        })
        todoAdapter.setItemCheckBoxClickListener(object : TodoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, itemId: Long, isChecked: Boolean) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todoList = viewModel.fetchDate(selectedDate.orEmpty()).value ?: return@launch
                    if (position < todoList.size) {
                        val updatedTodo =
                            todoList[position].copy(
                                isChecked = !todoList[position].isChecked,
                            )
                        viewModel.update(updatedTodo)

                        // Firebase Realtime Database에 업데이트
                        val todoItemRef = todoRef.child(itemId.toString())
                        todoItemRef.child("isChecked").setValue(!todoList[position].isChecked)
                        todoItemRef.child("todoFinish").setValue(!todoList[position].todoFinish)
                        val todoFinish=isChecked
                        todoRef.push().setValue(TodoList(isChecked=todoFinish))
                    }
                }
            }
        })
        fetchSchedule(selectedDate.orEmpty())
        fetchAccount(selectedDate.orEmpty())
    }
    private fun fetchSchedule(date: String) {
        if (date.isNotEmpty()) {
            scheduleViewModel.fetchSchedule(date).observe(viewLifecycleOwner) { scheduleList ->
                scheduleAdapter.setListData(scheduleList)
            }
        }
    }
    private fun fetchDate(date: String) {
        if (date.isNotEmpty()) {
            viewModel.fetchDate(date).observe(viewLifecycleOwner) { todoList ->
                todoAdapter.setListData(todoList)
            }
        }
    }
    private fun fetchAccount(date: String) {
        if (date.isNotEmpty()) {
            accountViewModel.fetchAccount(date).observe(viewLifecycleOwner) { accountList ->
                accountAdapter.setListData(accountList)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}