package com.example.scheduleapp
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ItemAccountBinding
import com.example.scheduleapp.listmodel.Appaccount
import com.example.scheduleapp.listmodel.State

class accountAdapter : ListAdapter<Appaccount, accountAdapter.accountItemViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountBinding.inflate(inflater, parent, false)
        return accountItemViewHolder(binding)
    } //item 레이아웃처럼 보이게

    override fun onBindViewHolder(holder: accountAdapter.accountItemViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        binding.titleAccountList.isVisible = position == 0
        if (position == 0) {
            val total =
                currentList.sumOf { if (it.state == State.income.name) it.money else -it.money }
            if (total >= 0) {
                binding.totalTextView.setTextColor(Color.parseColor("#6c81c8"))
                binding.totalTextView.text = "+$total won"
            } else {
                binding.totalTextView.setTextColor(Color.parseColor("#d4986e"))
                binding.totalTextView.text = "$total won"
            }
        }
        binding.nameTextView.text = item.name
        binding.memoTextView.text = item.memo
        binding.memoTextView.isVisible = item.memo?.isNotEmpty() == true
    }

    class accountItemViewHolder(val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root)
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Appaccount>() {
            //name으로 같은name과 같은 date를 가진 객체인지 확인
            override fun areItemsTheSame(oldItem: Appaccount, newItem: Appaccount): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }
            //위의 함수가 true를 반환할때만 , 즉 두 내용의 name이 같을때
            override fun areContentsTheSame(oldItem: Appaccount, newItem: Appaccount): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.memo == newItem.memo &&
                        oldItem.money == newItem.money &&
                        oldItem.date == newItem.date
            }
        }
    }
}