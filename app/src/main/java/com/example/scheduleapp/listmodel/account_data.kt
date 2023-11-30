package com.example.scheduleapp.listmodel

import java.util.Date

data class Appaccount(
    val state: String ="",
    val money: Int = 0,
    val name: String ="",
    val memo: String? = "", //memo가 입력되지 않아도 ㅇㅋ 이므로 nullable
    val date: Long = Date().time,
    val timestamp: Long = Date().time,
)

enum class State{
    income, expense
}
