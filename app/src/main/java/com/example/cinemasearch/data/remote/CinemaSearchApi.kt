package com.example.cinemasearch.data.remote

import com.example.cinemasearch.data.repository.MovieResponse
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
        @Query("sortField") sortField: String? = null,
        @Query("sortType") sortType: String? = null,
        @Query("limit") limit: Int = 50,
        @Query("query") query: String? = null
    ): MovieResponse

    @GET("v1.4/movie/search")
    suspend fun searchFilms(
        @Header("X-API-KEY") apiKey: String,
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): MovieResponse

}