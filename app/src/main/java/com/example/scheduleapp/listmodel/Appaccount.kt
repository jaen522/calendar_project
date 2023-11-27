package com.example.scheduleapp.listmodel

import java.util.Date

data class Appaccount(
    val state: String ="",
    val money: Int = 0,
    val name: String ="",
    val memo: String = "",
    val date: Long = Date().time,
    val timestamp: Long = Date().time,
)

enum class State{
    income, expense
}
