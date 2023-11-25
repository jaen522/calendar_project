package com.example.scheduleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class accountAdapter(private val accountList: List<String>) :
    RecyclerView.Adapter<accountAdapter.accountViewHolder>() {

    private val database = FirebaseDatabase.getInstance().reference
    private val databaseReference = database.child("account_node")
    class accountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView1)
        //val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView123)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_account, parent, false)
        return accountViewHolder(view)
    }

    override fun onBindViewHolder(holder: accountViewHolder, position: Int) {
        //holder.textView.text = accountList[position]
        val accdata = accountList[position]

        //holder.recyclerView.get = accdata
        holder.textView.text = accdata

        val datakey = "item_$position"
        databaseReference.child(datakey).setValue(accdata)

    }

    override fun getItemCount(): Int {
        return accountList.size
    }



}

/* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accadapter = accountAdapter(accountList)
      //  binding.recyclerView45.layoutManager = LinearLayoutManager(requireContext())
       // binding.recyclerView45.adapter = accadapter

 */
