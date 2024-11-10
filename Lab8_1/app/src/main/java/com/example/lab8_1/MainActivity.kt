package com.example.lab8_1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab8_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedPrefFile = "simpleDataApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy SharedPreferences
        val sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Xử lý nút "Lưu dữ liệu"
        binding.btnSave.setOnClickListener {
            val data = binding.etData.text.toString()
            if (data.isNotEmpty()) {
                with(sharedPreferences.edit()) {
                    putString("savedData", data)
                    apply()
                }
                Toast.makeText(this, "Dữ liệu đã được lưu", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút "Lấy dữ liệu"
        binding.btnRetrieve.setOnClickListener {
            val savedData = sharedPreferences.getString("savedData", null)
            if (savedData != null) {
                Toast.makeText(this, "Dữ liệu đã lưu: $savedData", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Không có dữ liệu được lưu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}