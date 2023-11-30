package com.example.scheduleapp.listmodel

import java.util.Date

data class Appschedule(
    val name: String = "",
    val memo: String = "",
    val date: Long = Date().time,
    val start: Long = Date().time,
    val end: Long = Date().time,
    val category: String = "",
    val timestamp: Long = Date().time
)

enum class Category{
    school, friends, workout
}
