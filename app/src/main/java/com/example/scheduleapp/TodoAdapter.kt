package com.example.scheduleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.database

class TodoAdapter(var items: MutableList<TodoList>):RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_todo,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount():Int = items.count()
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindItems(items:TodoList){
            val todoName: TextView = itemView.findViewById(R.id.t_name)
            val todoDate: TextView = itemView.findViewById(R.id.t_date)
            todoName.text=items.todoName
            todoDate.text=items.todoDate

        }
    }
    fun replaceList(newList: MutableList<TodoList>){
        items=newList.toMutableList()
        notifyDataSetChanged()
    }
}