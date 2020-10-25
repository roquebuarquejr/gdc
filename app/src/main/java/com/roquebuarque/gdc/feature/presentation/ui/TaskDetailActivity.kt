package com.roquebuarque.gdc.feature.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.roquebuarque.gdc.R
import kotlinx.android.synthetic.main.activity_task_detail.*

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnTaskDetailSave.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        Intent()
            .apply {
                edtTaskDetailName.text.toString()
                    .also {
                        putExtra(EXTRA_NAME, it)
                    }
                setResult(Activity.RESULT_OK, this)
            }.run {
                finish()
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

        const val EXTRA_NAME = "EXTRA_TASK_DETAIL_NAME"

        /**
         * Start [TaskDetailActivity]
         * @param context previous activity
         */
        fun start(context: Context): Intent {
            return Intent(context, TaskDetailActivity::class.java)
        }
    }
}