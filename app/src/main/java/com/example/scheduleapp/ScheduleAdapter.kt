package com.example.scheduleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ItemSheduleBinding
import com.example.scheduleapp.listmodel.Appschedule
import com.example.scheduleapp.listmodel.Category
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ScheduleAdapter:  ListAdapter<Appschedule, ScheduleAdapter.ScheduleItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):ScheduleItemViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSheduleBinding.inflate(inflater)
        return ScheduleItemViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ScheduleItemViewHolder,position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        binding.titleScheList.isVisible = position == 0

        when(Category.valueOf(item.category)) {
            Category.school -> binding.categoryIndicator.setImageResource(R.drawable.school)
            Category.friends -> binding.categoryIndicator.setImageResource(R.drawable.friends)
            Category.workout -> binding.categoryIndicator.setImageResource(R.drawable.workout)
        }

        binding.nameTextView.text = item.name

        val timeFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
        binding.timeTextView.text =
            "${timeFormat.format(Date(item.start))} - ${timeFormat.format(Date(item.end))}"


    }


    //class ItemView(view: View):RecyclerView.ViewHolder(view){
    //}
    class  ScheduleItemViewHolder(val binding: ItemSheduleBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Appschedule>() {
            override fun areItemsTheSame(oldItem: Appschedule, newItem: Appschedule): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }
            //위의 함수가 true를 반환할때만 , 즉 두 내용의 name이 같을때
            override fun areContentsTheSame(oldItem: Appschedule, newItem: Appschedule): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.memo == newItem.memo &&
                        oldItem.date == newItem.date
            }
        }
    }

}