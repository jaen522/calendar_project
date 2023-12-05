package com.example.scheduleapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduleapp.listmodel.Appschedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ScheduleRepo {
    private val database = Firebase.database
    private val scheduleRepo = database.getReference("schedule_list")
    private val liveData = MutableLiveData<List<Appschedule>>()

    init {
        scheduleRepo.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val appschedule = mutableListOf<Appschedule>()
                for (data in snapshot.children){
                    val item = data.getValue(Appschedule::class.java)
                    item?.let {
                        appschedule.add(it)
                    }
                }
                liveData.value = appschedule
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun list(selectedDate: String): LiveData<List<Appschedule>> {
        val liveData = MutableLiveData<List<Appschedule>>()
        scheduleRepo.orderByChild("scheduledate").equalTo(selectedDate)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val appschedule = mutableListOf<Appschedule>()
                    for (data in snapshot.children) {
                        val item = data.getValue(Appschedule::class.java)
                        item?.let {
                            appschedule.add(it)
                        }
                    }
                    liveData.value = appschedule
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        return liveData
    }
    fun insert(appschedule: Appschedule){
        val key = scheduleRepo.push().key
        key?.let {
            scheduleRepo.child(it).setValue(appschedule)
        }
    }
    fun update(appschedule: Appschedule){
        appschedule.scheduleid?.let {
            scheduleRepo.child(it).setValue(appschedule)
        }
    }
    fun delete(appschedule: Appschedule){
        appschedule.scheduleid?.let {
            scheduleRepo.child(it).removeValue()
        }
    }
}