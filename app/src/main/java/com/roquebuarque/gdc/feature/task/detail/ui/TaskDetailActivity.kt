package com.roquebuarque.gdc.feature.task.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.detail.TaskDetailViewModel

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            TaskDetailViewModel.TaskViewModelFactory(
                GdcApplication.instance
            )
        ).get(TaskDetailViewModel::class.java)

        viewModel.task.observe(this, Observer {
            Log.d("Roque", it.toString())
        })

        val id = intent.getLongExtra(EXTRA_TASK_ID, 0)
        viewModel.start(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
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