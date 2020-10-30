package com.roquebuarque.gdc.feature.task.add.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.add.TaskAddViewModel
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import kotlinx.android.synthetic.main.activity_task_new.*
import org.threeten.bp.OffsetDateTime

class TaskAddActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_new)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            TaskAddViewModel.TaskViewModelFactory(
                GdcApplication.instance
            )
        ).get(TaskAddViewModel::class.java)

        btnTaskNewSave.setOnClickListener {
            val name = edtTaskNewName.text.toString()
            if (name.isNotEmpty()) {
                viewModel.insert(TaskDto(name = name, date = OffsetDateTime.now()))
                finish()
            } else {
                Snackbar.make(edtTaskNewName, R.string.name_required, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object {
        /**
         * Start [TaskAddActivity]
         * @param context previous activity
         */
        fun start(context: Context): Intent {
            return Intent(context, TaskAddActivity::class.java)
        }
    }
}