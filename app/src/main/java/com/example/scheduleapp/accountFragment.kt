package com.example.scheduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduleapp.databinding.FragmentAccountBinding
import com.google.firebase.database.FirebaseDatabase

class accountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private val accountList = mutableListOf<String>()
    private lateinit var accadapter: accountAdapter
    private var currentState: State = State.income


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accadapter = accountAdapter(accountList)
        binding.recyclerView45.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView45.adapter = accadapter

        //state  설정
        binding.aStateIn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentState = State.income
                binding.aStateEx.isChecked =! isChecked
            }
        }

        binding.aStateEx.setOnCheckedChangeListener{ _, isChecked->
            if(isChecked){
                currentState = State.expense
                binding.aStateIn.isChecked =! isChecked
            }
        }

        binding.btnFinAccount.setOnClickListener {
            val accountstate = currentState
            val accountmoney = binding.aMoney.text.toString()
            val accountname = binding.aName.text.toString()
            val accountmemo = binding.aMemo.text.toString()
            val accountdate = binding.aDate.text.toString()

            //리스트에 넣기ㄴ
            val accdata = "$accountstate - $accountmoney - $accountmemo - $accountname -$accountdate"

            accountList.add(accdata)

            accadapter.notifyDataSetChanged()

            //firebase연동
            val database = FirebaseDatabase.getInstance()
            val myaccount = database.getReference("account_node")

            //firebase 데이터 추가하기
            myaccount.push().setValue(accdata)

            //입력했던 값 초기화
            binding.aMoney.text.clear()
            binding.aMemo.text.clear()
            binding.aName.text.clear()
            binding.aDate.text.clear()
            binding.aStateIn.isChecked = false
            binding.aStateEx.isChecked = false

            //새로운 어댑터생성및 설정
            accadapter = accountAdapter(accountList)
            binding.recyclerView45.adapter = accadapter

        }

    }

    /*
        //내비게이션에서 account의 맨 밑 버튼 createevent를 누르면 메인화면으로 넘어갈 수 있게
        view.findViewById<Button>(R.id.btn_fin_account)?.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_calendarFragment)
        }
*/
    /*
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }*/
}

