package com.example.scheduleapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentAccountBinding
import com.example.scheduleapp.listmodel.Appaccount
import com.example.scheduleapp.listmodel.State
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class accountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            //수입 지출 체크박스 관련
            aStateIn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    aStateEx.isChecked = !isChecked
                }
            }

            aStateEx.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    aStateIn.isChecked =!isChecked
                }
            }
            //날짜 설정, date칸 누르면 달력 통해서 날짜 설정할 수 있게
            calDate.setOnClickListener {
                showDatePicker()
            }
            //끝내기누르면 저장되게
            btnFinAccount.setOnClickListener {
                save()
            }

        }
    }

    //날짜 선택을 다이얼로그 이용하여 하게하는  함수
    private fun showDatePicker(){
        val selectedDate = this.date?: Calendar.getInstance()

        DatePickerDialog(requireContext()).apply {
            updateDate(
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )

            setOnDateSetListener { _, y1, m1, d1 ->
                val date = Calendar.getInstance().apply {
                    set(y1,m1,d1,0,0,0)
                    set(Calendar.MILLISECOND, 0)
                }
                this@accountFragment.date = date
            }
        }.show()
    }
    //데이터 저장하고 저장하고 나면 메인화면으로 이동
    private  fun  save() = with(binding){
        val state=
            if (aStateIn.isChecked) State.income
            else if (aStateEx.isChecked) State.expense
            else null
        val money = aMoney.text.toString().trim().toIntOrNull()
        val name = aName.text.toString().trim()
        val memo =aMemo.text.toString().trim()
        val date = this@accountFragment.date

        if (state == null) return@with
        if (money == null || money == 0) return@with
        if (name.isEmpty()) return@with
        if (date == null) return@with

        val account = Appaccount(state.name, money, name, memo, date.timeInMillis)

        //파이어베이스에 연동하여 저장
        Firebase.database.reference
            .child("account_list")
            .push()
            .setValue(account)

        //리셋
        aStateIn.isChecked = false
        aStateEx.isChecked = false
        aMoney.text.clear()
        aName.text.clear()
        aMemo.text.clear()
        this@accountFragment.date = null

        //이동
        findNavController().navigate(R.id.action_accountFragment_to_calendarFragment)

    }
}

