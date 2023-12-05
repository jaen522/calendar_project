package com.example.scheduleapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentScheduleBinding
import com.example.scheduleapp.listmodel.Appschedule
import com.example.scheduleapp.listmodel.Category
import com.example.scheduleapp.viewmodel.ScheduleViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ScheduleFragment : Fragment() {

    val scheduleviewmodel : ScheduleViewModel by activityViewModels()

    var binding: FragmentScheduleBinding? = null

    //날짜 선택
    /*private var date: Calendar? = null
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
        }*/


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
        //date 설정하려고 눌렀을 시 달력칸에서 선택할 수 있게
        //시각 선택할려고 눌렀을 시 시계에서 선택할 수 있게
        // 마지막 버튼 눌렀을때 입력값 저장될 수 있게.

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

    /**
     * 시간 선택 다이얼로그를 띄운다.
     * @param isStartTime 시작 시간을 설정한다면 true, 종료 시간을 설정한다면 false
     */
    private var selectedStartTime: Calendar? = null
    private var selectedEndTime: Calendar? = null
    private fun showStartTimePicker() {
        val currentTime = Calendar.getInstance()

        val startTimePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->

            selectedStartTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }

        // 여기에서 선택한 시작 시간을 활용하여 종료 시간을 선택하는 다이얼로그 호출
            showEndTimePicker()
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false)

    startTimePicker.setTitle("Select Start Time")
    startTimePicker.show()
    }

    private fun showEndTimePicker() {
        val currentTime = Calendar.getInstance()

        val endTimePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            // 종료 시간 선택
            val selectedEndTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }

            // 여기에서 선택한 시작 시간과 종료 시간을 활용하여 필요한 작업 수행
            handleSelectedTimeRange()
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false)

        endTimePicker.setTitle("Select End Time")
        endTimePicker.show()
    }

    // 선택한 시작 시간과 종료 시간을 이용한 작업 수행
    private fun handleSelectedTimeRange() {

        if (selectedStartTime != null && selectedEndTime != null) {

        // 시작 시간과 종료 시간은 각각 아래에 저장
            val selectedstartTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedStartTime!!.time)
            val selectedendTime = SimpleDateFormat("HH:mm",Locale.getDefault()).format(selectedEndTime!!.time)
        }
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
//시각 추가

        if (category==null) return
        if (schename == null) return
        if (schememo.isNullOrEmpty()) return
        if (schedate==null) return
        if (startTime >= endTime) return

        //firebase 연동
        Firebase.database.reference
            .child("schedule_list")
            .push()
            .setValue(Appschedule(null, category.name, schename, schememo ,schedate ,startTime,endTime))

        //리셋
        binding?.chkSchool?.isChecked = false
        binding?.chkFriends?.isChecked = false
        binding?.chkWorkout?.isChecked = false
        binding?.sName?.text?.clear()
        binding?.sMemo?.text?.clear()
        binding?.calDate?.text?.clear()
        binding?.sTimeStart?.text?.clear()
        binding?.sTimeEnd?.text?.clear()

        //시각 추가


        //메인화면 이동
        findNavController().navigate(R.id.action_scheduleFragment_to_calendarFragment)

    }
}

/*어케 수정하지
    private fun showDatePicker(){
        val selectedDate = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            // binding?.calDate?.text = "(${year}/${month+1}/${dayOfMonth})".Editable
            binding?.calDate?.text = Editable.Factory.getInstance().newEditable("${year}/${month + 1}/${dayOfMonth}")
        }

        DatePickerDialog(
            requireContext(),
            dateSetListener,
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
*/

/*시각 추가
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
*/