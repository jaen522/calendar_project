package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentScheduleBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ScheduleFragment : Fragment() {




    data class Schedule(
        val schedule_name: String, val schedule_memo: String,
        val schedule_date: String
    )

    private var binding: FragmentScheduleBinding? = null

    private val ScheduleList = mutableListOf<Schedule>()

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        val view = inflater.inflate(R.layout.fragment_schedule, container, false)



        //입력받아오기

        val snameTextView: EditText = view.findViewById(R.id.s_name)
        val smemoTextView: EditText = view.findViewById(R.id.s_memo)
        val sdateTextView: EditText = view.findViewById(R.id.s_date)
        // val stimesTextView: EditText = view.findViewById(R.id.s_time_Start)
        // val stimeeTextView: EditText = view.findViewById(R.id.s_time_end)
        val finishsch: Button = view.findViewById(R.id.finish_schedule)

        //creat event 버튼 클릭 시 입력된 내용이 저장되게
        finishsch.setOnClickListener {
            val schedule_name = snameTextView.text.toString()
            val schedule_memo = smemoTextView.text.toString()
            val schedule_date = sdateTextView.text.toString()
            // val schedule_time_start = stimesTextView.text.toString()
            //val schedule_time_end = stimeeTextView.text.toString()

            //데이터를 schedule 객체로 생성
            val schedule = Schedule(schedule_name, schedule_memo, schedule_date)

            //생성된 객체 리스트에 추가
            ScheduleList.add(schedule)

            //firebase에 데이터 업로드
            uploadDataToFirebase(schedule)

            //새로 입력받기 위해 입력 필드 초기화
            smemoTextView.text.clear()
            snameTextView.text.clear()
            sdateTextView.text.clear()
            // stimesTextView.text.clear()
            // stimeeTextView.text.clear()
        }

        return view
    }


    private fun uploadDataToFirebase(schedule: Schedule) {

        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("sches")

        val scheID = myRef.push().key
        if (scheID != null) {
            myRef.child(scheID).setValue(schedule)
        }
    }

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