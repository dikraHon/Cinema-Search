package com.example.cinemasearch.data.di

import android.content.Context
import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.data.remote.ApiClient
import com.example.cinemasearch.data.remote.CinemaSearchApi
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @ApiService
    fun providesApiService(): CinemaSearchApi = ApiClient.cinemaSearchApi()

    @Provides
    fun providesDatabase(context: Context): AppDatabase = AppDatabase.getDatabase(context)

}