package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentScheduleBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding

//날짜 선택
    private var date: Calendar? = null
        set(value) {
            field = value

            if (value == null) {
                binding.calDate.setText("")
            } else {
                    binding.calDate.setText(
                       SimpleDateFormat(
                          "yyyy/MM/dd",
                       Locale.KOREA
                       ).format(value.time)
                    )
            }
        }

    //시작시각 선택
    private var startTime: Pair<Int,Int> ?= null
    //끝시각 선택
    private var endTime: Pair<Int,Int> ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //카테고리 설정
            //date 설정하려고 눌렀을 시 달력칸에서 선택할 수 있게
            //시각 선택할려고 눌렀을 시 시계에서 선택할 수 있게
        // 마지막 버튼 눌렀을때 입력값 저장될 수 있게.

        view.findViewById<Button>(R.id.finish_schedule)?.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_calendarFragment)
        }
    }


}