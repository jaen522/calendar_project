package com.example.scheduleapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduleapp.TodoList
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class TodoRepo {
    fun getData(): LiveData<MutableList<TodoList>>{
        val mutableData=MutableLiveData<MutableList<TodoList>>()
        val database=Firebase.database
        val todoRef=database.getReference("Todo")
        todoRef.addValueEventListener(object :ValueEventListener{
            val listTodoData:MutableList<TodoList> = mutableListOf<TodoList>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (todoSnapshot in snapshot.children) {
                        val getData = todoSnapshot.getValue(TodoList::class.java)
                        listTodoData.add(getData!!)
                        mutableData.value = listTodoData
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableData
    }
}