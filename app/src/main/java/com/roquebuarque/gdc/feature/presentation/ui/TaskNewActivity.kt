package com.roquebuarque.gdc.feature.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.roquebuarque.gdc.R
import kotlinx.android.synthetic.main.activity_task_new.*

class TaskNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_new)

        //Add arrow back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnTaskNewSave.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        val name = edtTaskNewName.text.toString()
        if (name.isNotEmpty()) {
            Intent()
                .apply {
                    putExtra(EXTRA_NAME, name)
                    setResult(Activity.RESULT_OK, this)
                }.run {
                    finish()
                }
        } else {
            Snackbar.make(edtTaskNewName, R.string.name_required, Snackbar.LENGTH_LONG).show()
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
         * Start [TaskNewActivity]
         * @param context previous activity
         */
        fun start(context: Context): Intent {
            return Intent(context, TaskNewActivity::class.java)
        }
    }
}