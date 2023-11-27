package com.example.scheduleapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentTodoBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database

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
        inView()
    }
    private fun inView(){
        binding.finishTodo.setOnClickListener{
            val database = Firebase.database
            val todoRef=database.getReference("todo")
            val todoName = binding.tName.text.toString()
            val todoDate = binding.tDate.text.toString()

            todoRef.push().setValue(TodoList(todoName,todoDate))
            Log.d(TAG,"todoRef::$todoRef")
            binding.tName.text.clear()
            binding.tDate.text.clear()
            findNavController().navigate(R.id.action_todoFragment_to_calendarFragment)
        }
    }
}