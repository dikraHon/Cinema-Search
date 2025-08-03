package com.example.cinemasearch.data.repositoryImplementation.reposiroryImplPackage

import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.domain.repositoryPackage.FavoriteRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    getDao: AppDatabase
) : FavoriteRepository {
    private val favoritesDao = getDao.favoritesDao()

    override suspend fun addToFavorites(film: Films) {
        favoritesDao.insertFavoritesFilms(film.copy(isFavorite = true))
    }

    override suspend fun removeFromFavorites(filmId: Long) {
        favoritesDao.deleteFavoriteFilms(filmId)
    }

    override suspend fun getFavoritesFilms(): List<Films> {
        return favoritesDao.getFavoriteFilms()
    }

}