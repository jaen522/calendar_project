package com.example.scheduleapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentScheduleBinding
import com.example.scheduleapp.listmodel.Appschedule
import com.example.scheduleapp.listmodel.Category
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ScheduleFragment : Fragment() {

    var binding: FragmentScheduleBinding? = null

    //날짜 선택
    private var date: Calendar? = null
        set(value) {
            field = value

            if (value == null) {
                binding?.calDate?.setText("")
            } else {
                binding?.calDate?.setText(
                    SimpleDateFormat(
                        "yyyy/MM/dd",
                        Locale.KOREA
                    ).format(value.time)
                )
            }
        }
    //시각선택
    private var startTime: Pair<Int, Int>? = null
        set(value) {
            field = value

            if (value == null) {
                binding?.sTimeStart?.setText("")
            } else {
                binding?.sTimeStart?.setText(
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.KOREA
                    ).format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, value.first)
                        set(Calendar.MINUTE, value.second)
                    }.time)
                )
            }
        }
    //시각선택
    private var endTime: Pair<Int, Int>? = null
        set(value) {
            field = value

            if (value == null) {
                binding?.sTimeEnd?.setText("")
            } else {
                binding?.sTimeEnd?.setText(
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.KOREA
                    ).format(Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, value.first)
                        set(Calendar.MINUTE, value.second)
                    }.time)
                )
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding?.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //카테고리 설정
        binding?.chkSchool?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.chkWorkout?.isChecked = !isChecked
                binding?.chkFriends?.isChecked = !isChecked
            }
        }
        binding?.chkWorkout?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.chkSchool?.isChecked = !isChecked
                binding?.chkFriends?.isChecked = !isChecked
            }
        }
        binding?.chkFriends?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.chkWorkout?.isChecked = !isChecked
                binding?.chkSchool?.isChecked = !isChecked
            }
        }
        binding?.calDate?.setOnClickListener {
            showDatePicker()
        }
        binding?.sTimeStart?.setOnClickListener {
            showTimePicker(true)
        }
        binding?.sTimeEnd?.setOnClickListener {
            showTimePicker(false)
        }
        binding?.finishSchedule?.setOnClickListener {
            save()
        }
        //date 설정하려고 눌렀을 시 달력칸에서 선택할 수 있게
        //시각 선택할려고 눌렀을 시 시계에서 선택할 수 있게
        // 마지막 버튼 눌렀을때 입력값 저장될 수 있게.

    }

    private fun showDatePicker() {
        val selectedDate = this.date ?: Calendar.getInstance()

        DatePickerDialog(requireContext()).apply {  //require는 왜 쓰인건지
            updateDate( //updateDate 함수 어디에있는지 알아오기
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )

            setOnDateSetListener { _, y1, m1, d1 ->
                val date = Calendar.getInstance().apply {
                    set(y1, m1, d1, 0, 0, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                this@ScheduleFragment.date =date
            }
        }.show()
    }

    /**
     * 시간 선택 다이얼로그를 띄운다.
     * @param isStartTime 시작 시간을 설정한다면 true, 종료 시간을 설정한다면 false
     */
    private fun showTimePicker(isStartTime: Boolean) {
        val now = run {
            val calendar = Calendar.getInstance()
            return@run calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
        }

        val selectedTime = if (isStartTime) {
            startTime ?: now
        } else {
            endTime ?: now
        }

        TimePickerDialog(
            requireContext(),
            { _, p1, p2 ->
                if (isStartTime) {
                    startTime = p1 to p2
                } else {
                    endTime = p1 to p2
                }
            },
            selectedTime.first,
            selectedTime.second,
            false
        ).show()
    }

    private fun save() {
        val category =
            if (binding?.chkSchool?.isChecked == true) Category.school
            else if (binding?.chkFriends?.isChecked == true ) Category.friends
            else if (binding?.chkWorkout?.isChecked == true) Category.workout
            else null

        val name = binding?.sName?.text?.toString()?.trim()
        val memo = binding?.sMemo?.text?.toString()?.trim()
        val date = this@ScheduleFragment.date
        val startTime = this@ScheduleFragment.startTime
        val endTime = this@ScheduleFragment.endTime
//시각 추가

        if (category==null) return
        if (name == null) return
        if (memo.isNullOrEmpty()) return
        if (date==null) return
        if (((startTime?.first ?: 0) * 60 + (startTime?.second ?: 0)) >= ((endTime?.first?:0)*60 + (endTime?.second?: 0))){
            return
        }


//시각 추가
        val schedule = Appschedule(
            name,
            memo,
            date.timeInMillis,
            Calendar.getInstance().apply {
                time = date.time
                set(Calendar.HOUR_OF_DAY, startTime?.first ?: 0)
                set(Calendar.MINUTE, startTime?.second?: 0)
            }.timeInMillis,
            Calendar.getInstance().apply {
                time = date.time
                set(Calendar.HOUR_OF_DAY, endTime?.first?: 0)
                set(Calendar.MINUTE, endTime?.second ?: 0)
            }.timeInMillis,
            category.name,
        )
        //val schedule = Appschedule(name, memo, date.timeInMillis , category.name)

        //firebase 연동
        Firebase.database.reference
            .child("schedule_list")
            .push()
            .setValue(schedule)

        //리셋
        binding?.chkSchool?.isChecked = false
        binding?.chkFriends?.isChecked = false
        binding?.chkWorkout?.isChecked = false
        binding?.sName?.text?.clear()
        binding?.sMemo?.text?.clear()
        //시각 추가
        this@ScheduleFragment.date = null

        //메인화면 이동
        findNavController().navigate(R.id.action_scheduleFragment_to_calendarFragment)

    }
}