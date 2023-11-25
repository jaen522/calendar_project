package com.example.scheduleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class accountAdapter(private val accountList: List<String>) :
    RecyclerView.Adapter<accountAdapter.accountViewHolder>() {

    class accountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_account, parent, false)
        return accountViewHolder(view)
    }

    override fun onBindViewHolder(holder: accountViewHolder, position: Int) {
        holder.textView.text = accountList[position]
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

}
