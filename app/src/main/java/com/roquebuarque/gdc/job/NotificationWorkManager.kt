package com.roquebuarque.gdc.job

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity.Companion.SCHEDULE_EXTRA_TASK_NAME


class NotificationWorkManager(context: Context, parameters: WorkerParameters) : Worker(
    context,
    parameters
) {

    private val PRIMARY_CHANNEL_ID = "primary_channel_id"
    private val NOTIFICATION_ID = 0

    private var notificationWorkManager: NotificationManager? = null

    override fun doWork(): Result {
        val name = inputData.getString(SCHEDULE_EXTRA_TASK_NAME)
        return name?.let {

            createChannel()

            sendNotification(it)
            Result.success()
        } ?: Result.failure()

    }

    private fun sendNotification(taskName: String) {
        val notificationBuilder: NotificationCompat.Builder =
            getNotificationBuilder(taskName)

        //Delivery notification
        notificationWorkManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getNotificationBuilder(taskName: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(taskName)
            .setContentText(applicationContext.getText(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    }

    private fun createChannel() {

        //create a notification manager object
        notificationWorkManager = applicationContext
            .getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                applicationContext.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                applicationContext.getString(R.string.notification_channel_description)

            notificationWorkManager?.createNotificationChannel(notificationChannel)
        }



    }

}