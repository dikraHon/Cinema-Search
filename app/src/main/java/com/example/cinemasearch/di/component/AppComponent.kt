package com.example.cinemasearch.di.component

import android.app.Application
import android.content.Context
import com.example.cinemasearch.data.database.daoPackage.CollectionsDao
import com.example.cinemasearch.data.database.daoPackage.FavoritesDao
import com.example.cinemasearch.data.database.daoPackage.FilmsDao
import com.example.cinemasearch.di.module.AppModule
import com.example.cinemasearch.di.module.RepositoryModule
import com.example.cinemasearch.di.module.ViewModelModule
import com.example.cinemasearch.domain.repositoryPackage.CollectionRepository
import com.example.cinemasearch.domain.repositoryPackage.FavoriteRepository
import com.example.cinemasearch.domain.repositoryPackage.FilmRepository
import com.example.cinemasearch.presentation.MainActivity
import com.example.cinemasearch.presentation.viewModelPackage.FactoryViewModel
import dagger.BindsInstance
import dagger.Component
import jakarta.inject.Singleton

@Component(modules = [
    AppModule::class,
    ViewModelModule::class,
    RepositoryModule::class,]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance application: Application
        ): AppComponent
    }

    fun inject(activity: MainActivity)
    fun viewModelFactory(): FactoryViewModel
    fun filmRepository(): FilmRepository
    fun favoriteRepository(): FavoriteRepository
    fun collectionRepository(): CollectionRepository
    fun baseFilmsDao(): FilmsDao
    fun favoriteFilmsDao(): FavoritesDao
    fun collectionsDao(): CollectionsDao
}