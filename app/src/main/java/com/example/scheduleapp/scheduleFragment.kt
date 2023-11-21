package com.example.scheduleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R.id.s_name
import com.example.scheduleapp.databinding.FragmentScheduleBinding
import com.example.scheduleapp.viewmodel.schedule_viewmodel
import kotlin.time.Duration.Companion.seconds


class scheduleFragment : Fragment() {

   // var text: String? = null
    private var binding: FragmentScheduleBinding? = null
    //변수 연동을 위한 viewmodel 이용

    val scheduleViewmodel: schedule_viewmodel by activityViewModels()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text = arguments?.getString("text")
        //arguments?.let {
        //}
    }*/

    //변수들 선언
    private lateinit var schedule_name: String
    private lateinit var schedule_memo: String
    private lateinit var schedule_date: String
    private lateinit var schedule_time_start: String
    private lateinit var schedule_time_end: String
    private lateinit var schedule_category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val binding = FragmentScheduleBinding.inflate(inflater, container, false)
        //val schedule_name : TextView = findViewById(R.id.s_name)
        //binding.sName.setText(text) //name으로 입력받는거
        //return binding?.root

        binding = FragmentScheduleBinding.inflate(inflater,container,false)

        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        val btnschedule :Button = view.findViewById(R.id.finish_schedule)

        btnschedule.setOnClickListener {
            val ss_name: EditText = view.findViewById(R.id.s_name)
            val ss_memo: EditText = view.findViewById(R.id.s_memo)
            val ss_date: EditText = view.findViewById(R.id.s_date)
            val ss_time_start: EditText = view.findViewById(R.id.s_time_Start)
            val ss_time_end: EditText = view.findViewById(R.id.s_time_end)

            scheduleViewmodel.schedule_name = ss_name.text.toString()
            scheduleViewmodel.schedule_memo = ss_memo.text.toString()
            scheduleViewmodel.schedule_date = ss_date.text.toString()
            scheduleViewmodel.schedule_time_start = ss_time_start.text.toString()
            scheduleViewmodel.schedule_time_end = ss_time_end.text.toString()
        }
        return view
    }

    //저장이 제대로 되는지 확인하는 용
    internal fun getScheduleName(): String {
        return schedule_name
    }

    /*
    companion object {
        fun newInstance(str: String): scheduleFragment {
            val frag = scheduleFragment()
            frag.arguments = Bundle()
            frag.arguments?.putString("text", str)

            return frag
        }
    }
*/
    //내비게이션에서 schedule의 맨 밑 버튼 변수 finish_schedule을 누르면 메인화면으로 넘어갈 수 있게
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.finish_schedule)?.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_calendarFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}