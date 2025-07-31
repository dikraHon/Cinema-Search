package com.example.cinemasearch.di

import android.app.Application
import android.content.Context
import com.example.cinemasearch.presintation.MainActivity
import com.example.cinemasearch.presintation.viewModelPackage.FactoryViewModel
import com.example.cinemasearch.presintation.viewModelPackage.SearchFilmsViewModel
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance application: Application,
        ): AppComponent
    }

    fun inject(
        activity: MainActivity
    )

    fun viewModelFactory(): FactoryViewModel

    fun searchFilmViewModel(): SearchFilmsViewModel
}