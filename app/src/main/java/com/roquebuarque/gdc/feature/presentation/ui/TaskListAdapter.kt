package com.roquebuarque.gdc.feature.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.data.entity.TaskDto

class TaskListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<TaskDto>() // Cached copy of tasks

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskItemNameView: TextView = itemView.findViewById(R.id.txtItemTaskName)
        val taskItemStatusView: TextView = itemView.findViewById(R.id.txtItemTaskStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = tasks[position]
        holder.taskItemNameView.text = current.name
        holder.taskItemStatusView.text = current.state
    }

    internal fun submit(tasks: List<TaskDto>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size
}