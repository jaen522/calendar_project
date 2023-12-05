package com.example.scheduleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ListTodoBinding

class TodoAdapter(private val context: Context):RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    private val tdList= mutableListOf<TodoList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val binding = ListTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }    //날짜 선택

    override fun getItemCount():Int = tdList.size
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.onBind(tdList[position])
    }
    inner class ViewHolder(val binding: ListTodoBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(todo:TodoList?){
            todo?.let {
                binding.tdName.text=it.todoName
                if (it.todoImport) {
                    binding.tdName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                } else {
                    binding.tdName.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light))
                }

                binding.tdCheck.setOnClickListener {
                    itemCheckBoxClickListener.onClick(
                        it, layoutPosition, itemId, binding.tdCheck?.isChecked ?: false
                    )
                }
            }
        }
    }
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener
    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }
    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int, itemId:Long, isChecked:Boolean)
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
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem && oldItem.todoFinish == newItem.todoFinish
        }
    }
}