package com.example.lab8_5

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var etTask: EditText
    private lateinit var btnAddTask: Button
    private lateinit var rvTasks: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskList: MutableList<Task>
    private lateinit var adapter: TaskAdapter
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTask = findViewById(R.id.etTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvTasks = findViewById(R.id.rvTasks)
        sharedPreferences = getSharedPreferences("tasks_pref", MODE_PRIVATE)

        taskList = loadTasks()

        adapter = TaskAdapter(taskList, { task ->
            deleteTask(task)
        }, { task ->
            editTask(task)
        })

        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        btnAddTask.setOnClickListener {
            val taskName = etTask.text.toString().trim()
            if (taskName.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Lỗi")
                builder.setMessage("Vui lòng nhập tác vụ trước khi thêm!")
                builder.setPositiveButton("OK", null)
                builder.show()
            } else {
                addTask(taskName)
            }
        }

    }

    private fun addTask(taskName: String) {
        val newTask = Task(taskList.size + 1, taskName)
        taskList.add(newTask)
        saveTasks()
        adapter.notifyDataSetChanged()
        etTask.text.clear()
    }

    private fun deleteTask(task: Task) {
        taskList.remove(task)
        saveTasks()
        adapter.notifyDataSetChanged()
    }

    private fun editTask(task: Task) {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.setText(task.taskName)

        builder.setTitle("Chỉnh sửa tác vụ")
        builder.setView(input)
        builder.setPositiveButton("Lưu") { _, _ ->
            val newTaskName = input.text.toString().trim()
            if (newTaskName.isNotEmpty()) {
                task.taskName = newTaskName
                saveTasks()
                adapter.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Hủy", null)
        builder.show()
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(taskList)
        editor.putString("tasks", json)
        editor.apply()
    }

    private fun loadTasks(): MutableList<Task> {
        val json = sharedPreferences.getString("tasks", null)
        return if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}