package com.roquebuarque.gdc.feature.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.data.entity.TaskDto
import com.roquebuarque.gdc.feature.presentation.TaskListViewModel
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskListAdapter
    private val newTaskActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        adapter = TaskListAdapter(this)
        rvTaskList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(GdcApplication.instance)
        ).get(TaskListViewModel::class.java)

        setObserver()

        fabAddTask.setOnClickListener {
            val intent = TaskDetailActivity.start(this)
            startActivityForResult(intent, newTaskActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newTaskActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(TaskDetailActivity.EXTRA_NAME)?.let {
                val task = TaskDto(name = it)
                viewModel.addTask(task)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.operation_canceled,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun setObserver() {
        viewModel.alltasks.observe(this, Observer {
            adapter.submit(it)
        })
    }
}