package com.example.scheduleapp

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentTodoBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.util.Calendar

class todoFragment : Fragment() {
    private var binding:FragmentTodoBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTodoBinding.inflate(inflater,container,false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.finishTodo?.setOnClickListener {
            todosave()
            findNavController().navigate(R.id.action_todoFragment_to_calendarFragment)
        }
        binding?.tDate?.setOnClickListener {
            getDate()
        }
    }
    private fun getDate(){
        val cal= Calendar.getInstance()
        val dateSetListener= DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            binding?.tDate?.text = Editable.Factory.getInstance().newEditable("${year}/${month + 1}/${dayOfMonth}")
        }
        DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    private fun todosave(){
        val database= Firebase.database
        val todoRef=database.getReference("todo")
        val todoName=binding?.tName?.text.toString()
        val todoData=binding?.tDate?.text.toString()
        todoRef.push().setValue(TodoList(todoName,todoData))
        Log.d(TAG,"todoRef::$todoRef")
        binding?.tName?.text?.clear()
        binding?.tDate?.text?.clear()
    }
}