package com.example.cinemasearch.di.module

import android.content.Context
import androidx.work.WorkerParameters
import com.example.cinemasearch.data.DataUpdateWorker
import com.example.cinemasearch.domain.repositoryPackage.FilmRepository
import dagger.Module
import dagger.Provides

@Module
class WorkerModule {
    @Provides
    fun provideDataUpdateWorker(
        context: Context,
        workerParams: WorkerParameters,
        filmRepository: FilmRepository
    ): DataUpdateWorker {
        return DataUpdateWorker(context, workerParams)
    }
}