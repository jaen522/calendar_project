package com.example.scheduleapp
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ItemAccountBinding
import com.example.scheduleapp.listmodel.Appaccount


class accountAdapter(private val context: Context):RecyclerView.Adapter<accountAdapter.ViewHolder>() {
    val accList = mutableListOf<Appaccount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountAdapter.ViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = accList.size
    override fun onBindViewHolder(holder: accountAdapter.ViewHolder, position: Int) {
        holder.onBind(accList[position])
    }

    inner class ViewHolder(val binding: ItemAccountBinding) :RecyclerView.ViewHolder(binding.root) {

        fun onBind(account: Appaccount) {
            account?.let {
                binding.accountNameTextview.text = it.accountname
                binding.accountMemoTextview.text = it.accountmemo
                binding.accountMoneyTextview.text= it.accountmoney.toString()
            }
        }
    }
    fun setListData(newList: List<Appaccount>) {
        val diffCallback = AppaccountDiffCallback(accList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        Log.d("AccountAdapter", "Data before update: $accList")

        accList.clear()
        accList.addAll(newList)
        Log.d("AccountAdapter", "Data after update: $accList")

        diffResult.dispatchUpdatesTo(this)
    }

    private class AppaccountDiffCallback(
        private val oldList: List<Appaccount>,
        private val newList: List<Appaccount>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].accountid == newList[newItemPosition].accountid
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}



/*
class accountAdapter : ListAdapter<Appaccount, accountAdapter.accountItemViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountBinding.inflate(inflater, parent, false)
        return accountItemViewHolder(binding)
    } //item 레이아웃처럼 보이게

    override fun onBindViewHolder(holder: accountItemViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        binding.titleAccountList.isVisible = position == 0
        if (position == 0) {
            val total =
                currentList.sumOf { if (it.accountstate == State.income.name) it.accountmoney else -it.accountmoney }
            if (total >= 0) {
                binding.totalTextView.setTextColor(Color.parseColor("#6c81c8"))
                binding.totalTextView.text = "+$total won"
            } else {
                binding.totalTextView.setTextColor(Color.parseColor("#d4986e"))
                binding.totalTextView.text = "$total won"
            }
        }
        binding.nameTextView.text = item.accountname
        binding.memoTextView.text = item.accountmemo
        binding.memoTextView.isVisible = item.accountmemo?.isNotEmpty() == true
    }

}*/
