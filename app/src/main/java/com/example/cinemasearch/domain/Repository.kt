package com.example.cinemasearch.domain

interface Repository {

    suspend fun getAllFilms(): List<Films>

    suspend fun getFilmById(id: Long): Films

    suspend fun addFilmById(id: Long): Films

    suspend fun deleteFilmById(id: Long)

}