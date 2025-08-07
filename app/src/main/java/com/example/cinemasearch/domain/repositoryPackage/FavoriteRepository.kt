package com.example.cinemasearch.domain.repositoryPackage

import com.example.cinemasearch.domain.modelData.Films

interface FavoriteRepository {
    suspend fun addToFavorites(film: Films)
    suspend fun removeFromFavorites(filmId: Long)
    suspend fun getFavoritesFilms(): List<Films>
}