package com.example.scheduleapp
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ItemAccountBinding
import com.example.scheduleapp.listmodel.Appaccount


class accountAdapter(private val context: Context):RecyclerView.Adapter<accountAdapter.ViewHolder>() {
    private var accList = mutableListOf<Appaccount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountAdapter.ViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = accList.size
    override fun onBindViewHolder(holder: accountAdapter.ViewHolder, position: Int) {
        holder.onBind(accList[position])
    }

    inner class ViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val accountname: TextView = binding.nameTextView
        private val accountmemo: TextView = binding.memoTextView
        private val accountmoney: TextView = binding.moneyTextView


        fun onBind(data: Appaccount) {
            accountname.text = data.accountname
            accountmemo.text = data.accountmemo
            accountmoney.inputType = data.accountmoney


        }
    }
    //todo에서는 checkbox관련
    fun setListData(newList: List<Appaccount>) {
        val diffCallback = AppaccountDiffCallback(accList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        accList.clear()
        accList.addAll(newList)

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
    //  binding.memoTextView.isVisible = item.accountmemo?.isNotEmpty() == true


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

    class accountItemViewHolder(val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Appaccount>() {
            //name으로 같은name과 같은 date를 가진 객체인지 확인
            fun areItemsTheSame(oldItem: Appaccount, newItem: Appaccount): Boolean {
                return oldItem.accountid == newItem.accountid
            }

            //위의 함수가 true를 반환할때만 , 즉 두 내용의 name이 같을때
            fun areContentsTheSame(oldItem: Appaccount, newItem: Appaccount): Boolean {
                return oldItem.accountname == newItem.accountname &&
                        oldItem.accountmemo == newItem.accountmemo &&
                        oldItem.accountmoney == newItem.accountmoney &&
                        oldItem.accountdate == newItem.accountdate
            }
        }
    }
}*/
