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
    private lateinit var binding:FragmentTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.finish_todo)?.setOnClickListener {
            val todoName = binding.tName.text.toString()
            val todoDate = binding.tCheck.text.toString()
            val key = TDRef.contentRef.push().key.toString()
            // child()는 해당 키 위치로 이동하는 메서드로 child()를 사용하여 key 값의 하위에 값을 저장한다.
            // setValue() 메서드를 사용하여 값을 저장한다.
            TDRef.contentRef
                .child(key)
                .setValue(TodoList(todoName,todoDate))
            findNavController().navigate(R.id.action_todoFragment_to_calendarFragment)
        }
    }
}