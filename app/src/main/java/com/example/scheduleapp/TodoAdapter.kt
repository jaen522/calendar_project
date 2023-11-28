package com.example.scheduleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val context: Context):RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    private var tdList= mutableListOf<TodoList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_todo,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount():Int = tdList.size
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        val todoList:TodoList=tdList[position]
        holder.todoName.text=todoList.todoName
        holder.todoDate.text=todoList.todoDate
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val todoName: TextView = itemView.findViewById(R.id.t_name)
            val todoDate: TextView = itemView.findViewById(R.id.t_date)
    }
    fun setListData(data:MutableList<TodoList>){
        tdList=data
    }
}