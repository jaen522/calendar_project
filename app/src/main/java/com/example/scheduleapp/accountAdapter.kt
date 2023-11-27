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
    }

    override fun onBindViewHolder(holder: accountItemViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            titleAccountList.isVisible = position == 0
            if (position == 0) {
                val total =
                    currentList.sumOf { if (it.state == State.income.name) it.money else -it.money }
                if (total >= 0) {
                    totalTextView.setTextColor(Color.parseColor("#6c81c8"))
                    totalTextView.text = "+$total won"
                } else {
                    totalTextView.setTextColor(Color.parseColor("#d4986e"))
                    totalTextView.text = "$total won"
                }
            }

            nameTextView.text = item.name
            memoTextView.text = item.memo
            memoTextView.isVisible = item.memo.isNotEmpty()

            when (State.valueOf(item.state)) {
                State.income -> {
                    moneyTextView.setTextColor(Color.parseColor("#6c81c8"))
                    moneyTextView.text = "+${item.money} won"
                }

                State.expense -> {
                    moneyTextView.setTextColor(Color.parseColor("#d4986e"))
                    moneyTextView.text = "-${item.money} won"
                }
            }
        }
    }

    class accountItemViewHolder(val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Appaccount>() {
            override fun areItemsTheSame(oldItem: Appaccount, newItem: Appaccount): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }

            override fun areContentsTheSame(oldItem: Appaccount, newItem: Appaccount): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.memo == newItem.memo &&
                        oldItem.money == newItem.money &&
                        oldItem.date == newItem.date
            }
        }
    }
}
