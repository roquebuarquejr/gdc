package com.roquebuarque.gdc.job

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity.Companion.SCHEDULE_EXTRA_TASK_NAME

class NotificationWorkManager(context: Context, parameters: WorkerParameters): Worker(context, parameters) {
    override fun doWork(): Result {
        val name = inputData.getString(SCHEDULE_EXTRA_TASK_NAME)
       return name?.let {
            Log.d("Roque", it)
            Result.success()
        } ?: Result.failure()

    }
}