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
import com.example.scheduleapp.databinding.FragmentAccountBinding
import com.example.scheduleapp.viewmodel.schedule_viewmodel


class accountFragment : Fragment() {

    var binding : FragmentAccountBinding? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

     }*/

    private lateinit var account_money: String
    private lateinit var account_name: String
    private lateinit var account_memo: String
    private lateinit var account_date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = FragmentAccountBinding.inflate(inflater)
        //return binding?. root

        val view = inflater.inflate(R.layout.fragment_account, container, false)

        val a_money: EditText = view.findViewById(R.id.a_money)
        val a_name: EditText = view.findViewById(R.id.a_name)
        val a_memo: EditText = view.findViewById(R.id.a_memo)
        val a_date: EditText = view.findViewById(R.id.a_date)

        account_money = a_money.text.toString()
        account_name = a_name.text.toString()
        account_memo = a_memo.text.toString()
        account_date = a_date.text.toString()

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //내비게이션에서 account의 맨 밑 버튼 createevent를 누르면 메인화면으로 넘어갈 수 있게
        view.findViewById<Button>(R.id.finish_account)?.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_calendarFragment)
        }

        /*//체크용, 화면에 calerdar name띄우기 이 주석을 풀면 account로 안가던데 왜지
        val scheduleFragment = scheduleFragment()

        val checknameTextView: TextView = view.findViewById(R.id.chk_sname)
        checknameTextView.text = scheduleFragment.getScheduleName()
        */
    //체크용
        val scheduleViewmodel: schedule_viewmodel by activityViewModels()
        val check_sname = scheduleViewmodel.schedule_name


        val checksnameTextView: TextView = view.findViewById(R.id.chk_sname)
        checksnameTextView.text = check_sname

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}