package com.roquebuarque.gdc.feature.task.list.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import com.roquebuarque.gdc.feature.task.add.TaskAddActivity
import com.roquebuarque.gdc.feature.task.list.presentation.TaskListViewModel
import kotlinx.android.synthetic.main.activity_task_list.*
import java.lang.IllegalArgumentException

class TaskListActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskListAdapter
    private val newTaskActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        adapter =
            TaskListAdapter(
                this,
                ::taskListClickListener
            )
        rvTaskList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(GdcApplication.instance)
        ).get(TaskListViewModel::class.java)

        setObserver()

        fabAddTask.setOnClickListener {
            val intent =
                TaskAddActivity.start(
                    this
                )
            startActivityForResult(intent, newTaskActivityRequestCode)
        }

    }

    private fun taskListClickListener(taskId: Long) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra(TaskAddActivity.EXTRA_NAME)
                ?: throw IllegalArgumentException("No name passed by task detail")

            when (requestCode) {
                newTaskActivityRequestCode -> saveNewTask(name)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.operation_canceled,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveNewTask(name: String) {
        viewModel.insert(TaskDto(name = name))
    }


    private fun setObserver() {
        viewModel.alltasks.observe(this, Observer {
            adapter.submit(it)
        })
    }
}