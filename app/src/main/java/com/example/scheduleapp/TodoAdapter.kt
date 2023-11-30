package com.example.scheduleapp

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
        holder.onBind(tdList[position])
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var todoCheckbox = itemView.findViewById<CheckBox>(R.id.td_check)
        val todoName: TextView = itemView.findViewById(R.id.t_name)
        val todoDate: TextView = itemView.findViewById(R.id.t_date)
        fun onBind(data:TodoList){
            todoName.text=data.todoName
            todoCheckbox.isChecked=data.isChecked
            if (data.isChecked) {
                todoName.paintFlags = todoName.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                todoName.paintFlags = todoName.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }

            todoCheckbox.setOnClickListener{
                itemCheckBoxClickListener.onClick(it, layoutPosition,itemId)
            }
        }
    }
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener
    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }
    fun setListData(newList: List<TodoList>){
        tdList=newList.toMutableList()
        notifyDataSetChanged()
    }
    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int,itemId:Long)
    }

}