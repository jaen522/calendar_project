package com.example.scheduleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.listmodel.Appschedule
import com.example.scheduleapp.repository.ScheduleRepo

class ScheduleViewModel: ViewModel() {
    private val scherepo = ScheduleRepo()

    fun fetchDate(selectedDate: String): LiveData<List<Appschedule>>{
        return scherepo.list(selectedDate)
    }

}