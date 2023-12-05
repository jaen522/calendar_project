package com.example.scheduleapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.databinding.FragmentAccountBinding
import com.example.scheduleapp.listmodel.Appaccount
import com.example.scheduleapp.listmodel.State
import com.example.scheduleapp.viewmodel.AccountViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class accountFragment : Fragment() {

    //뷰모델 참조
    val accviewModel : AccountViewModel by activityViewModels()

   // private lateinit var binding : FragmentAccountBinding
    // var binding: Fragment어쩌구? = null 을 안 쓰는 이유?

    var binding : FragmentAccountBinding? = null

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
        }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰모델 이용
        //accviewModel.appaccount.observe(viewLifecycleOwner){ }


        //with함수 안쓰고 해보기
        binding?.aStateIn?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding?.aStateEx?.isChecked =! isChecked
            }
        }
        binding?.aStateEx?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding?.aStateIn?.isChecked =! isChecked
            }
        }
        binding?.calDate?.setOnClickListener {
             showDatePicker()
        }
        binding?.btnFinAccount?.setOnClickListener {
            save()
        }
    }

    //날짜 선택을 다이얼로그 이용하여 하게하는  함수
    private fun showDatePicker(){
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

            /*setOnDateSetListener { _, y1, m1, d1 ->
                val date = Calendar.getInstance().apply {
                    set(y1,m1,d1,0,0,0)
                    set(Calendar.MILLISECOND, 0)
                }
            }*/
        }.show()
    }
    /*
    val cal= Calendar.getInstance()
        val dateSetListener= DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            binding?.tDate?.text = Editable.Factory.getInstance().newEditable("${year}/${month + 1}/${dayOfMonth}")
        }
        DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
     */

    private fun save() {
        val state =
            if (binding?.aStateIn?.isChecked == true) State.income
            else if (binding?.aStateEx?.isChecked == true ) State.expense
            else null

        val acc_money = binding?.aMoney?.text?.toString()?.toIntOrNull()
        val acc_name = binding?.aName?.text?.toString()
        val acc_memo = binding?.aMemo?.text?.toString()
        val acc_date = binding?.calDate?.text.toString()
        //val date = this@accountFragment.date

        if (state==null) return
        if (acc_money == null || acc_money == 0) return
        if (acc_name==null) return
        if (acc_memo.isNullOrEmpty()) return
        if (acc_date==null) return


        //firebase 연동
        Firebase.database.reference
            .child("account_list")
            .push()
            .setValue(Appaccount( null, state.name , acc_money,acc_name, acc_memo, acc_date))

        //리셋
        binding?.aStateIn?.isChecked = false
        binding?.aStateEx?.isChecked = false
        binding?.aMoney?.text?.clear()
        binding?.aName?.text?.clear()
        binding?.aMemo?.text?.clear()
        binding?.calDate?.text?.clear()
        // this@accountFragment.date = null

        //메인화면 이동
        findNavController().navigate(R.id.action_accountFragment_to_calendarFragment)

    }
    /*
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

        //메인으로 이동
        findNavController().navigate(R.id.action_accountFragment_to_calendarFragment)

    }*/
}

