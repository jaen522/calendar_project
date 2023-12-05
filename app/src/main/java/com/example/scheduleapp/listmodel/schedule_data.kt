package com.example.scheduleapp.listmodel

import java.util.Date

data class Appschedule(
    val scheduleid: String? = null,
    val schedulename: String = "",
    val schedulememo: String? = "",
    val scheduledate: String = "",
    val schedulestart: String ="",
    val scheduleend: String = "",
    val schedulecategory: String = "",
    val timestamp: Long = Date().time
)

enum class Category{
    school, friends, workout
}
