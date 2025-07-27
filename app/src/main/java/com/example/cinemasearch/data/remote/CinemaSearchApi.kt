package com.example.cinemasearch.data.remote

import com.example.cinemasearch.domain.Films
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CinemaSearchApi {

    @GET("v1.4/movie")
    suspend fun getFilms(
        @Header("X-API-KEY") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): MovieResponse
    @GET("v1.4/movie/{id}")
    suspend fun getFilmById(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Long
    ): Films

    @POST("films")
    suspend fun addFilm(
        @Body filmItem: Films
    ): Films

}