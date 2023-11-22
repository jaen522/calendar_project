package com.example.scheduleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ListTodoBinding

class TodoAdapter(val layoutTodo: Array<LayoutTodo>):RecyclerView.Adapter<TodoAdapter.TodoHolder>(){
    private var data= mutableListOf<LayoutTodo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val binding = ListTodoBinding.inflate(LayoutInflater.from(parent.context))
        return TodoHolder(binding)
    }

    override fun getItemCount() = layoutTodo.size

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(layoutTodo[position])
    }
    inner class TodoHolder(private val binding: ListTodoBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(layoutTodo: LayoutTodo){
            binding.tdCheck.text= layoutTodo.name
        }
    }
    fun replaceList(newList: MutableList<LayoutTodo>){
        data=newList.toMutableList()
        notifyDataSetChanged()
    }
}