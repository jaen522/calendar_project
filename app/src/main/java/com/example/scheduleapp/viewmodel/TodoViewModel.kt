package com.example.scheduleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.TodoList
import com.example.scheduleapp.repository.TodoRepo

class TodoViewModel:ViewModel() {
    private val todorepo=TodoRepo()
    fun fetchDate():LiveData<MutableList<TodoList>>{
        val mutableData=MutableLiveData<MutableList<TodoList>>()
        todorepo.getData().observeForever{
            mutableData.value=it
        }
        return mutableData
    }
}