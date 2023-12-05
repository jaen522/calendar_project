package com.example.scheduleapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduleapp.listmodel.Appaccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AccountRepo {
    private val  database = Firebase.database
    private val accountRepo = database.getReference("account_list")
    private val liveData = MutableLiveData<List<Appaccount>>()

    init {
        accountRepo.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val appaccount= mutableListOf<Appaccount>()
                for (data in snapshot.children){
                    val item = data.getValue(Appaccount::class.java)
                    item?.let {
                        appaccount.add(it)
                    }
                }
                liveData.value = appaccount
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun list(selectedDate: String) : LiveData<List<Appaccount>> {
        val liveData = MutableLiveData<List<Appaccount>>()
        accountRepo.orderByChild("accountdate").equalTo(selectedDate)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val appaccount = mutableListOf<Appaccount>()
                    for (data in snapshot.children) {
                        val item = data.getValue(Appaccount::class.java)
                        item?.let {
                            appaccount.add(it)
                        }
                    }
                        liveData.value = appaccount
                    }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        return liveData
    }
    fun insert(appaccount: Appaccount){
        val key = accountRepo.push().key
        key?.let {
            accountRepo.child(it).setValue(appaccount)
        }
    }
    fun update(appaccount: Appaccount){
        appaccount.accountid?.let {
            accountRepo.child(it).setValue(appaccount)
        }
    }
    fun delete(appaccount: Appaccount){
        appaccount.accountid?.let {
            accountRepo.child(it).removeValue()
        }
    }


}