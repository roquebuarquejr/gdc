package com.roquebuarque.gdc.job

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity
import com.roquebuarque.gdc.feature.task.list.presentation.ui.TaskListActivity


/**
 * The Service that JobScheduler runs once the conditions are met.
 * In this case it posts a notification.
 */
class NotificationJobService : JobService() {

    private var mNotifyManager: NotificationManager? = null

    /**
     * Called by the system once it determines it is time to run the job.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether or not the job was offloaded to a
     * separate thread.
     * In this case, it is false since the notification can be posted on
     * the main thread.
     */
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        val taskName = jobParameters.extras.getString(TaskAddActivity.SCHEDULE_EXTRA_TASK_NAME)
        Log.d(TAG, taskName ?: throw  IllegalArgumentException("no name"))

        sendNotification(taskName)
        return false
    }

    /**
     * Called by the system when the job is running but the conditions are no
     * longer met.
     * In this example it is never called since the job is not offloaded to a
     * different thread.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether the job needs rescheduling.
     */
    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    private fun createNotificationChannel() {

        // Create a notification manager object.
        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "TASKS",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Channel description"
            mNotifyManager?.createNotificationChannel(notificationChannel)
        }
    }


    /**
     * OnClick method for the "Notify Me!" button.
     * Creates and delivers a simple notification.
     */
    private fun sendNotification(taskName: String) {
        createNotificationChannel()

        // Build the notification with all of the parameters using helper
        // method.
        val notifyBuilder: NotificationCompat.Builder = getNotificationBuilder(taskName)

        // Deliver the notification.
        mNotifyManager?.notify(NOTIFICATION_ID, notifyBuilder.build())


    }

    /**
     * Helper method that builds the notification.
     *
     * @return NotificationCompat.Builder: notification build with all the
     * parameters.
     */
    private fun getNotificationBuilder(taskName: String): NotificationCompat.Builder {

        // Set up the pending intent that is delivered when the notification
        // is clicked.
        val notificationIntent = Intent(this, TaskListActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(
            this, NOTIFICATION_ID, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification with all of the parameters.
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("Let's do it")
            .setContentText("Time for your $taskName task")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true).setContentIntent(notificationPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
    }

    companion object {
        private val TAG: String = NotificationJobService::class.java.name
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        private const val NOTIFICATION_ID = 0

    }
}