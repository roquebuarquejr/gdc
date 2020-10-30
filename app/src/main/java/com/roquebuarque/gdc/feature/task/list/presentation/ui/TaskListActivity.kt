package com.roquebuarque.gdc.feature.task.list.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity
import com.roquebuarque.gdc.feature.task.detail.ui.TaskDetailActivity
import com.roquebuarque.gdc.feature.task.list.presentation.TaskListViewModel
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskListAdapter

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
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> viewModel.deleteAll()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }


    private fun taskListClickListener(taskId: Long) {
        startActivity(TaskDetailActivity.start(this, taskId))
    }

    private fun setObserver() {
        viewModel.alltasks.observe(this, Observer {
            adapter.submit(it)
        })
    }
}