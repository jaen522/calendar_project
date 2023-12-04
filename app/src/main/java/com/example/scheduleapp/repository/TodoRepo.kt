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
    private val database = Firebase.database
    private val todoRef = database.getReference("todo")
    private val liveData = MutableLiveData<List<TodoList>>()
    init {
        todoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todolist = mutableListOf<TodoList>()
                for (data in snapshot.children) {
                    val item = data.getValue(TodoList::class.java)
                    item?.let {
                        todolist.add(it)
                    }
                }
                liveData.value = todolist
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun list(selectedDate: String): LiveData<List<TodoList>> {
        val liveData = MutableLiveData<List<TodoList>>()
        todoRef.orderByChild("todoDate").equalTo(selectedDate)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val todolist = mutableListOf<TodoList>()
                    for (data in snapshot.children) {
                        val item = data.getValue(TodoList::class.java)
                        item?.let {
                            todolist.add(it)
                        }
                    }
                    liveData.value = todolist
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        return liveData
    }
    fun insert(todoList: TodoList) {
        val key = todoRef.push().key
        key?.let {
            todoRef.child(it).setValue(todoList)
        }
    }
    fun update(todoList: TodoList) {
        todoList.id?.let {
            todoRef.child(it).setValue(todoList)
        }
    }
    fun delete(todoList: TodoList) {
        todoList.id?.let {
            todoRef.child(it).removeValue()
        }
    }
}
