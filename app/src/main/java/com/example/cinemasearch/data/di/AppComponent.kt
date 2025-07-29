package com.example.cinemasearch.data.di

import android.app.Application
import android.content.Context
import com.example.cinemasearch.MainActivity
import com.example.cinemasearch.presintation.FactoryViewModel
import com.example.cinemasearch.presintation.SearchFilmsViewModel
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