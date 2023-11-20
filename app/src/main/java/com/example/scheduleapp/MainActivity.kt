package com.example.scheduleapp

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scheduleapp.databinding.ActivityMainBinding
import java.sql.Date

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val navController=binding.frgNav.getFragment<NavHostFragment>().navController
        binding.bottomNav.setupWithNavController(navController)
        setContentView(binding.root)
        }
    override fun onSupportNavigateUp(): Boolean {
        val navController=binding.frgNav.getFragment<NavHostFragment>().navController
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}