package com.example.scheduleapp

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ListTodoBinding

class TodoAdapter(private val context: Context):RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    private var tdList= mutableListOf<TodoList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val binding = ListTodoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }
    override fun getItemCount():Int = tdList.size
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.onBind(tdList[position])
    }
    inner class ViewHolder(private val binding: ListTodoBinding):RecyclerView.ViewHolder(binding.root){
        private val todoCheckbox: CheckBox = binding.tdCheck
        private val todoName: TextView = binding.tdName
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
    fun setListData(newList: List<TodoList>) {
        val diffCallback = TodoListDiffCallback(tdList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        tdList.clear()
        tdList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }
    private class TodoListDiffCallback(
        private val oldList: List<TodoList>,
        private val newList: List<TodoList>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int,itemId:Long)
    }

}