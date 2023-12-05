package com.example.scheduleapp

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
    /*private var date: Calendar? = null
        set(value) {
            field = value

            if (value == null) {
                binding?.calDate?.setText("")
            } else {
                binding?.calDate?.setText(
                    SimpleDateFormat(
                        "yyyy/MM/dd",
                        Locale.KOREA
                    ).format(value.time)
                )
            }
        }
    //시각선택
    private var startTime: Pair<Int, Int>? = null
        set(value) {
            field = value

            if (value == null) {
                binding?.sTimeStart?.setText("")
            } else {
                binding?.sTimeStart?.setText(
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.KOREA
                    ).format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, value.first)
                        set(Calendar.MINUTE, value.second)
                    }.time)
                )
            }
        }
    //시각선택
    private var endTime: Pair<Int, Int>? = null
        set(value) {
            field = value

            if (value == null) {
                binding?.sTimeEnd?.setText("")
            } else {
                binding?.sTimeEnd?.setText(
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.KOREA
                    ).format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, value.first)
                        set(Calendar.MINUTE, value.second)
                    }.time)
                )
            }
        }*/
    override fun getItemCount():Int = tdList.size
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.onBind(tdList[position])
    }
    inner class ViewHolder(val binding: ListTodoBinding):RecyclerView.ViewHolder(binding.root){

        private var todoFinish = itemView.findViewById<CheckBox>(R.id.td_check)
        fun onBind(todo:TodoList?){
            todo?.let {
                binding.tdName.text=it.todoName
                if (it.todoImport) {
                    binding.tdName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                } else {
                    binding.tdName.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light))
                }

                todoFinish.isChecked = it.isChecked
                if (it.todoFinish) {
                    // isChecked가 false이고 todoImport가 true인 경우에만 빨간색으로 설정
                    binding.tdName.paintFlags = binding.tdName.paintFlags or STRIKE_THRU_TEXT_FLAG
                } else {
                    binding.tdName.paintFlags =binding.tdName.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                }
                binding.tdCheck.setOnClickListener {
                    itemCheckBoxClickListener.onClick(
                        it,layoutPosition,itemId,todoFinish.isChecked,todoFinish.isChecked
                    )
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
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem && oldItem.todoFinish == newItem.todoFinish
        }
    }
    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int,itemId:Long,isChecked:Boolean,todoFinish:Boolean)
    }
}