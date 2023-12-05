package com.example.scheduleapp.listmodel

data class Appaccount(
    val accountid: String? = null,
    val accountstate: String = "",
    val accountmoney: Int = 0,
    val accountname: String ="",
    val accountmemo: String? = "", //memo가 입력되지 않아도 ㅇㅋ 이므로 nullable
    val accountdate: String = ""

//val timestamp: Long = Date().time,
)

enum class State{
    income, expense
}
