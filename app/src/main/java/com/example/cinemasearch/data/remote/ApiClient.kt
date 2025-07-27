package com.example.cinemasearch.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object ApiClient {

    fun moshi(): Moshi = Moshi
        .Builder()
        .add( KotlinJsonAdapterFactory() )
        .build()

    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/")
        .client(OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build())
        .addConverterFactory(MoshiConverterFactory.create(moshi()))
        .build()

    fun cinemaSearchApi(): CinemaSearchApi = retrofit()
        .create(
            CinemaSearchApi::class.java
        )
}