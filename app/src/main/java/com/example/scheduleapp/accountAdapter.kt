package com.example.scheduleapp
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.ItemAccountBinding
import com.example.scheduleapp.listmodel.Appaccount


class accountAdapter(private val context: Context):RecyclerView.Adapter<accountAdapter.ViewHolder>() {
    val accList = mutableListOf<Appaccount>() //recycleview에는 동적인 데이터셋을 관리할수있는 가변성인 mutable이 굿

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
            //데이터를 받아 해당 뷰 홀더의 레이아웃에 데이터(account)를 설정
            account?.let {    //null이 아닌 경우에만 다음코드 진행
                binding.accountNameTextview.text = it.accountname
                binding.accountMemoTextview.text = it.accountmemo
                binding.accountMoneyTextview.text= it.accountmoney.toString() //number 형식으로 받았기에 tostring해주기

                if ( it.accountstate == "income" ) {
                    binding.accountMoneyTextview.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
                    binding.accountStateTextview.text = "+"
                    binding.accountStateTextview.setTextColor(ContextCompat.getColor(context,android.R.color.holo_blue_light))
                }
                else{
                    binding.accountMoneyTextview.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                    binding.accountStateTextview.text = "-"
                    binding.accountStateTextview.setTextColor(ContextCompat.getColor(context,android.R.color.holo_red_light))
                }
            }
        }
    }
    fun setListData(newList: List<Appaccount>) {
        //새로운 정보 저장시키는 함수
        val diffCallback = AppaccountDiffCallback(accList, newList) // 새로운 데이터와  이전의 데이터 리스트 간의 차이를 계싼
        val diffResult = DiffUtil.calculateDiff(diffCallback) // 두 데이터 리스트 간의 차이를 게싼하고 이를 담은 diffresult 객체를 ㅂ반환
        accList.clear() //이전데이터인 리스트를 비우고
        accList.addAll(newList) //새로운 데이터 리스트로 업데이트
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


