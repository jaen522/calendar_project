package com.example.scheduleapp.listmodel

data class Appschedule(
    val scheduleid: String? = null,
    val schedulename: String = "",
    val schedulememo: String? = "",
    val scheduledate: String = "",
    val schedulestart: String ="",
    val scheduleend: String = "",
    val schedulecategory: String = ""
)

enum class Category{
    school, friends, workout
}
