package com.roquebuarque.gdc.feature.task.detail.ui

import android.app.NotificationManager
import android.app.job.JobScheduler
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.data.entity.Status.*
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import com.roquebuarque.gdc.feature.task.detail.TaskDetailViewModel
import com.roquebuarque.gdc.job.NotificationUtil
import kotlinx.android.synthetic.main.activity_task_detail.*


class TaskDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var selectedTask: TaskDto
    private lateinit var selectedStatus: String
    private  var taskId : Long = 0

    private val status = arrayOf(
        TODO.name,
        PROGRESS.name,
        DONE.name
    )

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startStatus()
        viewModel = ViewModelProvider(
            this,
            TaskDetailViewModel.TaskViewModelFactory(
                GdcApplication.instance,
                NotificationUtil
            )
        ).get(TaskDetailViewModel::class.java)

        viewModel.task.observe(this, {
            selectedTask = it
            edtTaskDetailName.setText(it.name)

            status.forEachIndexed { index, item ->
                if (item == it.state) {
                    selectedStatus = item
                    spinnerTaskDetailStatus.setSelection(index)
                }
            }
        })

        taskId = intent.getLongExtra(EXTRA_TASK_ID, 0)
        viewModel.start(taskId)

        btnUpdate.setOnClickListener {
            val name = edtTaskDetailName.text.toString()
            if (name.isNotEmpty()) {
                val updatedTask = selectedTask.copy(name = name, state = selectedStatus)
                viewModel.update(taskDto = updatedTask)
                Snackbar.make(btnUpdate, R.string.updated_successful, Snackbar.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, R.string.name_required, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_detail, menu)
        return super.onCreateOptionsMenu(menu)

    }

    private fun startStatus() {
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            status
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTaskDetailStatus.adapter = adapter

        spinnerTaskDetailStatus.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = status[position]
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_delete -> {
                viewModel.task.removeObservers(this)
                viewModel.delete(this, selectedTask.id)
                Snackbar.make(btnUpdate, R.string.deleted_successful, Snackbar.LENGTH_LONG).show()
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object {

        private const val EXTRA_TASK_ID = "EXTRA_TASK_DETAIL_ID"

        /**
         * Start [TaskDetailActivity]
         * @param context previous activity
         */
        fun start(context: Context, taskId: Long): Intent {
            return Intent(context, TaskDetailActivity::class.java)
                .apply { putExtra(EXTRA_TASK_ID, taskId) }
        }
    }
}