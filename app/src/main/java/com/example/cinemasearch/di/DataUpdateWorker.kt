package com.example.cinemasearch.di

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.cinemasearch.presentation.NotificationHelper
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class DataUpdateWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        NotificationHelper.showDownloadNotification(applicationContext, 0)

        return try {
            val app = applicationContext as? MyApp ?: return Result.failure()

            // Имитация прогресса загрузки
            for (i in 1..3) {
                delay(1000)
                NotificationHelper.showDownloadNotification(applicationContext, i * 33)
            }

            app.appComponent.filmRepository().updatePopularFilms()
            app.appComponent.filmRepository().updateTopRatedFilms()
            app.appComponent.filmRepository().updateNewReleases()

            NotificationHelper.showUpdateCompleteNotification(applicationContext)
            Result.success()
        } catch (e: Exception) {
            NotificationHelper.showErrorNotification(applicationContext, e.message ?: "Unknown error")
            Result.retry()
        }
    }

    companion object {
        private const val WORK_TAG = "filmDataUpdate"
        private const val REPEAT_INTERVAL_HOURS = 4L

        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<DataUpdateWorker>(
                REPEAT_INTERVAL_HOURS, TimeUnit.HOURS
            ).setConstraints(constraints).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}