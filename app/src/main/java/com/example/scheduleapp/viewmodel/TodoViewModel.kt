package com.example.scheduleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.TodoList
import com.example.scheduleapp.repository.TodoRepo

class TodoViewModel:ViewModel() {

    private val todorepo=TodoRepo()
    fun fetchDate(selectedDate:String):LiveData<List<TodoList>>{
        return todorepo.list(selectedDate)
    }
    fun update(todoList: TodoList) {
        todorepo.update(todoList)
    }
}