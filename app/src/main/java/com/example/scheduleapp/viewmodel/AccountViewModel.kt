package com.example.scheduleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.listmodel.Appaccount
import com.example.scheduleapp.repository.AccountRepo

class AccountViewModel : ViewModel() {
    private val accountrepo = AccountRepo()

    fun fetchAccount(selectedDate: String):LiveData<List<Appaccount>>{
        return accountrepo.list(selectedDate)
    }

}