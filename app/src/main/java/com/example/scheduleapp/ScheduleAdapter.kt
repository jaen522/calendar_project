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
/*
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
/*
//class ScheduleAdapter:  ListAdapter<Appschedule, ScheduleAdapter.ScheduleItemViewHolder>(diffUtil) {
    //class ItemView(view: View):RecyclerView.ViewHolder(view){}

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
       */