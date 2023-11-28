package com.example.scheduleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter:RecyclerView.Adapter<ScheduleAdapter.ItemView>() {

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):ItemView{
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_shedule,parent,false)
        return ItemView(view)
    }
    override fun onBindViewHolder(holder: ItemView,position: Int) {
        holder
    }
    override fun getItemCount(): Int {
        return 0
    }

    class ItemView(view: View):RecyclerView.ViewHolder(view){

    }
}