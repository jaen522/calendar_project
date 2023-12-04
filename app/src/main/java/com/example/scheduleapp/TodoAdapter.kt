package com.example.scheduleapp

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ListTodoBinding

class TodoAdapter(private val context: Context):RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    val tdList= mutableListOf<TodoList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val binding = ListTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount():Int = tdList.size
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.onBind(tdList[position])
    }
    inner class ViewHolder(val binding: ListTodoBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(todo:TodoList?){
            todo?.let {
                binding.tdName.text=it.todoName
                binding.tdCheck.isChecked=it.isChecked
                if (it.isChecked) {
                    binding.tdName.paintFlags = binding.tdName.paintFlags or STRIKE_THRU_TEXT_FLAG
                } else {
                    binding.tdName.paintFlags = binding.tdName.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }

                binding.tdCheck.setOnClickListener{
                    itemCheckBoxClickListener.onClick(it, layoutPosition,itemId)
                }
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