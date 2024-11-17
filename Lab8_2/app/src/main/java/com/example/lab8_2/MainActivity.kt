package com.example.lab8_2

import android.content.Context
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab8_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Keys for SharedPreferences
    private val PREFS_NAME = "user_preferences"
    private val DARK_MODE_KEY = "dark_mode"
    private val FONT_SIZE_KEY = "font_size"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load settings
        loadSettings()

        // Dark mode toggle
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModeSetting(isChecked)
            applyDarkMode(isChecked)
        }

        // Font size change
        binding.radioGroupFontSize.setOnCheckedChangeListener { _, checkedId ->
            val fontSize = when (checkedId) {
                R.id.radioSmall -> "small"
                R.id.radioMedium -> "medium"
                R.id.radioLarge -> "large"
                else -> "medium"
            }
            saveFontSizeSetting(fontSize)
            applyFontSize(fontSize)
        }
    }

    private fun loadSettings() {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load dark mode
        val isDarkMode = sharedPref.getBoolean(DARK_MODE_KEY, false)
        binding.switchDarkMode.isChecked = isDarkMode
        applyDarkMode(isDarkMode)

        // Load font size
        val fontSize = sharedPref.getString(FONT_SIZE_KEY, "medium") ?: "medium"
        when (fontSize) {
            "small" -> binding.radioGroupFontSize.check(R.id.radioSmall)
            "medium" -> binding.radioGroupFontSize.check(R.id.radioMedium)
            "large" -> binding.radioGroupFontSize.check(R.id.radioLarge)
        }
        applyFontSize(fontSize)
    }

    private fun saveDarkModeSetting(isDarkMode: Boolean) {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(DARK_MODE_KEY, isDarkMode)
            apply()
        }
    }

    private fun saveFontSizeSetting(fontSize: String) {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(FONT_SIZE_KEY, fontSize)
            apply()
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        val backgroundColor = if (isDarkMode) android.R.color.black else android.R.color.white
        val textColor = if (isDarkMode) android.R.color.white else android.R.color.black

        // Đặt màu nền
        binding.root.setBackgroundResource(backgroundColor)

        // Đặt màu chữ cho các thành phần liên quan
        binding.switchDarkMode.setTextColor(resources.getColor(textColor, theme))
        binding.radioGroupFontSize.forEach { (it as RadioButton).setTextColor(resources.getColor(textColor, theme)) }

        // Đặt màu chữ cho các TextView (Chế độ hiển thị và Kích thước chữ)
        binding.root.findViewById<TextView>(R.id.textDisplayMode).setTextColor(resources.getColor(textColor, theme))
        binding.root.findViewById<TextView>(R.id.textFontSize).setTextColor(resources.getColor(textColor, theme))
    }


    private fun applyFontSize(fontSize: String) {
        val textSize = when (fontSize) {
            "small" -> 14f
            "medium" -> 18f
            "large" -> 22f
            else -> 18f
        }
        binding.switchDarkMode.textSize = textSize
        binding.radioGroupFontSize.forEach { (it as RadioButton).textSize = textSize }
    }

    private inline fun RadioGroup.forEach(action: (RadioButton) -> Unit) {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is RadioButton) action(view)
        }
    }
}
