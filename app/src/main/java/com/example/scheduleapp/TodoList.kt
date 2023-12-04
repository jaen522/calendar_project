package com.example.scheduleapp

data class TodoList(val todoName:String="",
                    val todoDate:String="",
                    val isChecked:Boolean=false,
                    val todoFinish: Boolean = false,
                    val id:String?=null){
    val todoImport:Boolean=isChecked
}
