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

    suspend fun getFilmDetails(id: Long): Films

    suspend fun createCollection(name: String)
    suspend fun getAllCollections(): List<String>
    suspend fun addFilmToCollection(filmId: Long, collectionName: String)

    suspend fun getCollection(collectionName: String): List<Films>

}