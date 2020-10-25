package com.roquebuarque.gdc.feature.task.list.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto

class TaskListAdapter internal constructor(
    context: Context,
    private val listener: (Long) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<TaskDto>() // Cached copy of tasks

    inner class TaskViewHolder(itemView: View, private val listener: (Long) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val taskItemNameView: TextView = itemView.findViewById(R.id.txtItemTaskName)
        private val taskItemStatusView: TextView = itemView.findViewById(R.id.txtItemTaskStatus)
        private lateinit var taskDto: TaskDto

        fun bind(data: TaskDto) {
            taskDto = data
            taskItemNameView.text = data.name
            taskItemStatusView.text = data.state

            itemView.setOnClickListener {
                listener.invoke(taskDto.id)
            }
        }

        override fun onClick(p0: View?) {
            listener.invoke(taskDto.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    internal fun submit(tasks: List<TaskDto>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size
}