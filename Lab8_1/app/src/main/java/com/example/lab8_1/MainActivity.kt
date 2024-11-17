package com.example.lab8_1

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các View từ XML
        val etInput = findViewById<EditText>(R.id.etInput)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnRetrieve = findViewById<Button>(R.id.btnRetrieve)

        // SharedPreferences object
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Nút "Lưu dữ liệu"
        btnSave.setOnClickListener {
            try {
                val inputText = etInput.text.toString()
                if (inputText.isNotEmpty()) {
                    sharedPreferences.edit().putString("savedData", inputText).apply()
                    Toast.makeText(this, "Dữ liệu đã được lưu!", Toast.LENGTH_SHORT).show()
                } else {
                    throw IllegalArgumentException("Trường nhập liệu trống!")
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Đã xảy ra lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Nút "Lấy dữ liệu"
        btnRetrieve.setOnClickListener {
            try {
                val savedData = sharedPreferences.getString("savedData", null)
                if (savedData != null && savedData.isNotEmpty()) {
                    Toast.makeText(this, "Dữ liệu: $savedData", Toast.LENGTH_SHORT).show()
                } else {
                    throw IllegalStateException("Không có dữ liệu nào được lưu!")
                }
            } catch (e: IllegalStateException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Đã xảy ra lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
