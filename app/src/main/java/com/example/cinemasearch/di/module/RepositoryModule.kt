package com.example.cinemasearch.di.module

import com.example.cinemasearch.data.repositoryImplementation.reposiroryImplPackage.CollectionRepositoryImpl
import com.example.cinemasearch.data.repositoryImplementation.reposiroryImplPackage.FavoriteRepositoryImpl
import com.example.cinemasearch.data.repositoryImplementation.reposiroryImplPackage.FilmRepositoryImpl
import com.example.cinemasearch.domain.repositoryPackage.CollectionRepository
import com.example.cinemasearch.domain.repositoryPackage.FavoriteRepository
import com.example.cinemasearch.domain.repositoryPackage.FilmRepository
import dagger.Binds
import dagger.Module
import jakarta.inject.Singleton

@Suppress("unused")
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFilmRepository(impl: FilmRepositoryImpl): FilmRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindCollectionRepository(impl: CollectionRepositoryImpl): CollectionRepository
}