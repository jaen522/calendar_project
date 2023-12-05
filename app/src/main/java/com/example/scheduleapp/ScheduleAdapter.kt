package com.example.scheduleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ItemScheduleBinding
import com.example.scheduleapp.listmodel.Appschedule

class ScheduleAdapter(private val context: Context):RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    val scheList = mutableListOf<Appschedule>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = scheList.size

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        holder.onBind(scheList[position])
    }

    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(schedule: Appschedule) {
            schedule?.let {
                binding.scheduleNameTextview.text = it.schedulename
                binding.scheduleMemoTextview.text = it.schedulememo
                binding.scheduleStarttimeTextview.text = it.schedulestart
                binding.scheduleEndtimeTextview.text = it.scheduleend

                if (it.schedulecategory == "school") {
                    binding.scheduleNameTextview.setTextColor(ContextCompat.getColor(context, android.R.color.holo_purple))
                }
                else if (it.schedulecategory == "friends") {
                    binding.scheduleNameTextview.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                }
                else{
                    binding.scheduleNameTextview.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
                }
            }
        }
    }

    fun setListData(newList: List<Appschedule>) {
        val diffCallback = AppScheduleDiffCallback(scheList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        scheList.clear()
        scheList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)

    }

    private class AppScheduleDiffCallback(
        private val oldList: List<Appschedule>,
        private val newList: List<Appschedule>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size

        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].scheduleid == newList[newItemPosition].scheduleid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}
