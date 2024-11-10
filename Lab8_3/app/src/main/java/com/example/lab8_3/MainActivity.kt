package com.example.lab8_3

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab8_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Key for SharedPreferences
    private val PREFS_NAME = "launch_preferences"
    private val LAUNCH_COUNT_KEY = "launch_count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load and update the launch count
        val launchCount = updateLaunchCount()

        // Display the launch count on the screen
        binding.tvLaunchCount.text = "Số lần mở ứng dụng: $launchCount"
    }

    private fun updateLaunchCount(): Int {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentCount = sharedPref.getInt(LAUNCH_COUNT_KEY, 0)
        val newCount = currentCount + 1

        // Save the updated launch count
        with(sharedPref.edit()) {
            putInt(LAUNCH_COUNT_KEY, newCount)
            apply()
        }

        return newCount
    }
}