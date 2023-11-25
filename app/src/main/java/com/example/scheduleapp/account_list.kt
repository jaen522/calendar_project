package com.example.scheduleapp

enum class State{
    income, expense
}

data class account_list (val account_state : String,
                         val account_money: String,
                         val account_name: String,
                         val account_memo: String,
                         val account_date : String)



