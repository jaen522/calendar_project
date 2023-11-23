package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.scheduleapp.databinding.FragmentCalendarBinding


class calendarFragment : Fragment() {
    //private lateinit var todoAdapter: TodoAdapter
    private val mDatas= mutableListOf<LayoutTodo>()
    lateinit var binding: FragmentCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCalendarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*todoAdapter=TodoAdapter()
        mDatas.apply {
            LayoutTodo.add()
        }
        todoAdapter.replaceList(mDatas)
        binding.recTodo.adapter=todoAdapter
    */
    }


}