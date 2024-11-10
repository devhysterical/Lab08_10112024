package com.example.lab8_5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: List<Task>,
    private val onDeleteTask: (Task) -> Unit,
    private val onEditTask: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.tvTaskName)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)

        fun bind(task: Task) {
            taskName.text = task.taskName
            btnDelete.setOnClickListener { onDeleteTask(task) }
            btnEdit.setOnClickListener { onEditTask(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}

