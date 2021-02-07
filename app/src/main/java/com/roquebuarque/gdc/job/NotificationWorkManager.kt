package com.roquebuarque.gdc.job

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity


class NotificationWorkManager(context: Context, parameters: WorkerParameters) : Worker(
    context,
    parameters
) {

    override fun doWork(): Result {
        val name = inputData.getString(TaskAddActivity.EXTRA_TASK_NAME)
        val id = inputData.getLong(TaskAddActivity.EXTRA_TASK_ID, 0)
        return name?.let {
            NotificationUtil.sendNotification(applicationContext, it, id)
            Result.success()
        } ?: Result.failure()

    }
}