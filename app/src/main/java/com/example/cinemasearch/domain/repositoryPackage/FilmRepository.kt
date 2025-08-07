package com.example.cinemasearch.domain.repositoryPackage

import com.example.cinemasearch.domain.modelData.Films

interface FilmRepository {
    suspend fun getAllFilms(): List<Films>
    suspend fun getPopularFilms(): List<Films>
    suspend fun getTopRatedFilms(): List<Films>
    suspend fun getNewReleases(): List<Films>
    suspend fun searchFilms(query: String): List<Films>
    suspend fun getFilmDetails(id: Long): Films

    suspend fun updatePopularFilms()
    suspend fun updateTopRatedFilms()
    suspend fun updateNewReleases()

    suspend fun getAllFilmsFromCache(): List<Films>
}