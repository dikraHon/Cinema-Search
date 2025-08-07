package com.example.cinemasearch.presentation.componentsPack

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cinemasearch.R

object  NotificationHelper {
    private const val CHANNEL_ID = "cinema_updates"
    private const val CHANNEL_NAME = "Cinema Updates"
    private var notificationId = 0

    @SuppressLint("ObsoleteSdkInt")
    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description =
                        context.getString(R.string.notifications_about_film_updates_and_downloads)
                }

                val manager = context.getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            } catch (e: Exception) {
                e.message
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
                    return
                }
            }

            val manager = NotificationManagerCompat.from(context)
            manager.notify(++notificationId, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}