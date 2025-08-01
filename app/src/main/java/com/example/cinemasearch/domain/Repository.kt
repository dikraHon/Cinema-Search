package com.example.cinemasearch.domain

interface Repository {

    suspend fun getAllFilms(): List<Films>

    suspend fun getPopularFilms(): List<Films>

    suspend fun getTopRatedFilms(): List<Films>

    suspend fun getNewReleases(): List<Films>
    suspend fun addToFavorites(films: Films)

    suspend fun removeFromFavorites(filmId: Long)

    suspend fun getFavoritesFilms(): List<Films>

    suspend fun searchFilms(query: String): List<Films>

   // suspend fun getFilmsPage(page: Int): List<Films>

}