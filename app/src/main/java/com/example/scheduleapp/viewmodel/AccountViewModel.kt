package com.example.scheduleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.listmodel.Appaccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
/*
출력시키는 파일에서 적어야할것
onviewcreated 안에다가
AccountViewModel.accountlist.observe(viewLifecycleOwner){
    binding?.입력될칸?.text = viewModel.accountList.value
}
 */

class AccountViewModel : ViewModel() {

    private val _accountList = MutableLiveData<List<Appaccount>>()
    val accountlist : LiveData<List<Appaccount>> get() = _accountList

    //firebase에서 계정 데이터를 가져오고 '_accountlist' livedata를 업데이트하는 일을 담당
    // 프래그먼트에서 이 함수를 호출하여 계정 데이터의 변경 사항 관찰 ㅇ가능
    fun fetchAccountData(){
        Firebase.database.reference.child("account_list")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val accounts = mutableListOf<Appaccount>()
                    for (accountSnapshot in snapshot.children){
                        val account = accountSnapshot.getValue(Appaccount::class.java)
                        account?.let { accounts.add(it) }
                    }
                    _accountList.value = accounts
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }





}