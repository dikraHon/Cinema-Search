package com.example.cinemasearch.di.module

import android.app.Application
import android.content.Context
import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.data.remote.ApiClient
import com.example.cinemasearch.data.remote.CinemaSearchApi
import com.example.cinemasearch.di.qualifier.ApiService
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @ApiService
    fun providesApiService(): CinemaSearchApi = ApiClient.cinemaSearchApi()

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase = AppDatabase.Companion.getDatabase(context)

    @Provides
    @Singleton
    fun provideBaseFilmsDao(database: AppDatabase) = database.filmsDao()

    @Provides
    @Singleton
    fun provideFavoriteFilmsDao(database: AppDatabase) = database.favoritesDao()

    @Provides
    @Singleton
    fun provideCollectionsDao(database: AppDatabase) = database.collectionsDao()

}