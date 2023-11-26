package com.example.scheduleapp

enum class Cate{
    school, friends, workout
}

data class schedule_list(val schedule_name: String,
                         val schedule_memo: String,
                         val schedule_date: String,
                         val schedule_category: String)
