package com.example.cinemasearch.di

import android.app.Application
import com.example.cinemasearch.di.component.AppComponent
import com.example.cinemasearch.di.component.DaggerAppComponent
import java.util.concurrent.TimeUnit

class MyApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(context = this, application = this)
    }
}