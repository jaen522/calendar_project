package com.example.scheduleapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduleapp.databinding.FragmentCalendarBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
        getTDContentData()
    }
    private fun getTDContentData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todoList.clear()
                for (data in snapshot.children) {
                    val item = data.getValue(TodoList::class.java)
                    Log.d("calenderFragment", "item: ${item}")
                    // 리스트에 읽어 온 데이터를 넣어준다.
                    item?.let{
                        todoList.add(it)
                    }
                }
                todoList.reverse()
                // notifyDataSetChanged()를 호출하여 adapter에게 값이 변경 되었음을 알려준다.
                todoAdapter.replaceList(todoList)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        // addValueEventListener() 메서드로 DatabaseReference에 ValueEventListener를 추가한다.
        TDRef.contentRef.addValueEventListener(postListener)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}