package com.example.scheduleapp

import com.google.firebase.Firebase
import com.google.firebase.database.database

class TDRef {
    companion object {
        private val database = Firebase.database

        val contentRef = database.getReference("content")
    }
}