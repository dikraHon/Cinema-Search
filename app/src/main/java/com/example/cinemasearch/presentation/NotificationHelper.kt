package com.example.cinemasearch.presentation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cinemasearch.R

object  NotificationHelper {
    private const val TAG = "NotificationDebug"
    private const val CHANNEL_ID = "cinema_updates"
    private const val CHANNEL_NAME = "Cinema Updates"
    private var notificationId = 0

    init {
        Log.d(TAG, "NotificationHelper initialized")
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Notifications about film updates and downloads"
                }

                val manager = context.getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
                log("Notification channel created")
            } catch (e: Exception) {
                log("ERROR creating channel: ${e.message}")
            }
        }
    }

    fun showDownloadNotification(context: Context, progress: Int, max: Int = 100) {
        createChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_film)
            .setContentTitle(context.getString(R.string.notification_loading_title))
            .setContentText(context.getString(R.string.notification_loading_message))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(max, progress, false)
            .setOngoing(true)
            .setAutoCancel(true)

        showNotification(context, builder)
    }

    fun showUpdateCompleteNotification(context: Context) {
        createChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_film)
            .setContentTitle(context.getString(R.string.notification_success_title))
            .setContentText(context.getString(R.string.notification_success_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        showNotification(context, builder)
    }

    fun showErrorNotification(context: Context, errorMessage: String) {
        createChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_film)
            .setContentTitle(context.getString(R.string.update_error))
            .setContentText(errorMessage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        showNotification(context, builder)
    }

    private fun showNotification(context: Context, builder: NotificationCompat.Builder) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    log("ERROR: Missing notification permission")
                    return
                }
            }

            val manager = NotificationManagerCompat.from(context)
            manager.notify(++notificationId, builder.build())
            log("Notification shown with ID: $notificationId")
        } catch (e: Exception) {
            log("CRITICAL ERROR: ${e.javaClass.simpleName}: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun log(message: String) {
        Log.d(TAG, message)
    }
}