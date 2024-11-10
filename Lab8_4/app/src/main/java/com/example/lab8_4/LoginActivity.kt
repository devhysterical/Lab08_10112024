package com.example.lab8_4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab8_4.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val PREFS_NAME = "login_prefs"
    private val KEY_IS_LOGGED_IN = "is_logged_in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kiểm tra trạng thái đăng nhập
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(KEY_IS_LOGGED_IN, false)) {
            navigateToMainScreen()
        }

        // Xử lý nút đăng nhập
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username == "admin" && password == "admin") { // Điều kiện đăng nhập cơ bản
                with(sharedPref.edit()) {
                    putBoolean(KEY_IS_LOGGED_IN, true)
                    apply()
                }
                navigateToMainScreen()
            } else {
                Toast.makeText(this, "Sai tên người dùng hoặc mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}