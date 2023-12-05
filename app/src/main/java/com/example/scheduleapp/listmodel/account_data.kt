package com.example.scheduleapp.listmodel

data class Appaccount(
    val accountid: String? = null,
    val accountstate: String = "",
    val accountmoney: Int = 0,
    val accountname: String ="",
    val accountmemo: String? = "",
    val accountdate: String = ""

)

enum class State{
    income, expense
}
