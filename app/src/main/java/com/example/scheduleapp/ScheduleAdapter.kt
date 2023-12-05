package com.example.scheduleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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

    //여기가 그 출력시키는 코드 같은데 todo의 경우에 색깔 코드길래 그래서 여기다 적으면 될둣
    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        holder.onBind(scheList[position])
    }
/* 여기에 그 카테고리 마다 이미지 다르게 띄우는 코드 넣으면 돼
이미 적혀있는 코드는 혹시 몰라서 그냥 두었어 없애도 돼
        //val item = getItem(position)
        val binding = holder.binding

        binding.titleScheList.isVisible = position == 0

        when (Category.valueOf(item.category)) {
            Category.school -> binding.categoryIndicator.setImageResource(R.drawable.school)
            Category.friends -> binding.categoryIndicator.setImageResource(R.drawable.friends)
            Category.workout -> binding.categoryIndicator.setImageResource(R.drawable.workout)
        }

        binding.nameTextView.text = item.name

        val timeFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
        binding.timeTextView.text =
            "${timeFormat.format(Date(item.start))} - ${timeFormat.format(Date(item.end))}"
    }*/


    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(schedule: Appschedule?) {
            schedule?.let {
                binding.scheduleNameTextview.text = it.schedulename
                binding.scheduleMemoTextview.text = it.schedulememo
                binding.scheduleStarttimeTextview.text = it.schedulestart
                binding.scheduleEndtimeTextview.text = it.scheduleend
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
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem  //뭐 더 있긴함
        }
    }

}
