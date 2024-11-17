package com.example.lab8_3

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvAppOpenCount: TextView
    private lateinit var btnReset: Button

    // SharedPreferences keys
    private val PREFS_NAME = "app_preferences"
    private val OPEN_COUNT_KEY = "open_count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các view
        tvAppOpenCount = findViewById(R.id.tvAppOpenCount)
        btnReset = findViewById(R.id.btnReset)

        // Lấy số lần mở ứng dụng từ SharedPreferences
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val openCount = sharedPref.getInt(OPEN_COUNT_KEY, 0)

        // Hiển thị số lần mở
        tvAppOpenCount.text = openCount.toString()

        // Tăng số lần mở ứng dụng
        val newOpenCount = openCount + 1
        with(sharedPref.edit()) {
            putInt(OPEN_COUNT_KEY, newOpenCount)
            apply()
        }

        // Xử lý sự kiện reset số lần mở
        btnReset.setOnClickListener {
            with(sharedPref.edit()) {
                putInt(OPEN_COUNT_KEY, 0)
                apply()
            }
            tvAppOpenCount.text = "0"
        }
    }
}