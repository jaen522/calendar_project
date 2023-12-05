package com.example.scheduleapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

   // val scheduleviewmodel : ScheduleViewModel by activityViewModels()

    var binding: FragmentScheduleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
            showStartTimePicker()
        }
        binding?.sTimeEnd?.setOnClickListener {
            showEndTimePicker()
        }
        binding?.finishSchedule?.setOnClickListener {
            save()
        }
    }

    private fun showDatePicker() {
        val selectedDate = Calendar.getInstance()

        DatePickerDialog(requireContext()).apply {  //require는 왜 쓰인건지
            updateDate( //updateDate 함수 어디에있는지 알아오기
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )

            setOnDateSetListener { _, year, month, dayOfMonth ->
                binding?.calDate?.text = Editable.Factory.getInstance().newEditable("${year}/${month+1}/${dayOfMonth}")
            }
        }.show()
    }

    private var selectedStartTime: Calendar? = null
    private var selectedEndTime: Calendar? = null
    private fun showStartTimePicker() {
        val currentTime = Calendar.getInstance()

        val startTimePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->

            selectedStartTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }

            val selectedStartTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedStartTime!!.time)
            val sTimetextview = view?.findViewById<TextView>(R.id.s_time_start)
            sTimetextview?.text = selectedStartTime

        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false)

        startTimePicker.setTitle("Select Start Time")
        startTimePicker.show()
    }

    private fun showEndTimePicker() {
        val currentTime = Calendar.getInstance()

        val endTimePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            // 종료 시간 선택
            selectedEndTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            val selectedendTime = SimpleDateFormat("HH:mm",Locale.getDefault()).format(selectedEndTime!!.time)
            val eTimetextview = view?.findViewById<TextView>(R.id.s_time_end)
            eTimetextview?.text = selectedendTime

        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false)

        endTimePicker.setTitle("Select End Time")
        endTimePicker.show()
    }

    private fun save() {
        val category =
            if (binding?.chkSchool?.isChecked == true) Category.school
            else if (binding?.chkFriends?.isChecked == true ) Category.friends
            else if (binding?.chkWorkout?.isChecked == true) Category.workout
            else null

        val schename = binding?.sName?.text?.toString()
        val schememo = binding?.sMemo?.text?.toString()
        val schedate = binding?.calDate?.text?.toString()
        val startTime = binding?.sTimeStart?.text.toString()
        val endTime = binding?.sTimeEnd?.text.toString()

        if (category==null) return
        if (schename == null) return
        if (schememo.isNullOrEmpty()) return
        if (schedate==null) return
        if (startTime >= endTime) return

        //firebase 연동
        Firebase.database.reference
            .child("schedule_list")
            .push()
            .setValue(Appschedule(null, schename, schememo ,schedate ,startTime,endTime,category.name))

        //리셋
        binding?.chkSchool?.isChecked = false
        binding?.chkFriends?.isChecked = false
        binding?.chkWorkout?.isChecked = false
        binding?.sName?.text?.clear()
        binding?.sMemo?.text?.clear()
        binding?.calDate?.text?.clear()
        binding?.sTimeStart?.text?.clear()
        binding?.sTimeEnd?.text?.clear()

        //메인화면 이동
        findNavController().navigate(R.id.action_scheduleFragment_to_calendarFragment)

    }
}
