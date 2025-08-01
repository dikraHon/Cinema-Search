package com.example.cinemasearch.data.repository

import android.util.Log
import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.di.ApiService
import com.example.cinemasearch.data.remote.ApiConfig
import com.example.cinemasearch.data.remote.CinemaSearchApi
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApiService private val apiService: CinemaSearchApi,
    private val getDao: AppDatabase
) : Repository {
    private val apiKey = ApiConfig.API_KEY
    private val filmDao = getDao.filmsDao()

    override suspend fun getAllFilms(): List<Films> {
        return try {
            val response = apiService.getFilms(apiKey)
            processFilmResponse(response.docs)
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun searchFilms(query: String): List<Films> {
        return try {
            val response = apiService.searchFilms(apiKey, query)
            processFilmResponse(response.docs)
        } catch (e: Exception){
            emptyList()
        }
    }

    override suspend fun getPopularFilms(): List<Films> {
        return try {
            val response = apiService.getFilms(
                apiKey = apiKey,
                sortField = "rating.kp",
                sortType = "-1",
                limit = 20
            )
            processFilmResponse(response.docs)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getTopRatedFilms(): List<Films> {
        return try {
            val response = apiService.getFilms(
                apiKey = apiKey,
                sortField = "votes.kp",
                sortType = "-1",
                limit = 20
            )
            processFilmResponse(response.docs)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getNewReleases(): List<Films> {
        return try {
            val response = apiService.getFilms(
                apiKey = apiKey,
                sortField = "premiere.world", // Используем поле премьеры
                sortType = "-1", // Сортировка по убыванию (новые сначала)
                limit = 20
            )
            processFilmResponse(response.docs)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun processFilmResponse(films: List<FilmDTO>): List<Films> {
        return films.map { filmDto ->
            val imdbRating = filmDto.rating?.imdb ?: 0.0
            val rating = if (imdbRating == 0.0) {
                generateRandomRating()
            } else {
                imdbRating
            }
            Films(
                id = filmDto.id,
                name = filmDto.name ?: "no name",
                description = filmDto.description ?: "no description",
                poster = filmDto.poster?.url ?: "",
                rating = rating,
                year = filmDto.year ?: 0
            )
        }
    }

    override suspend fun getFavoritesFilms(): List<Films> {
        return filmDao.getFavoriteFilms()
    }

    override suspend fun addToFavorites(films: Films) {
        return filmDao.insertFavoritesFilms(films)
    }

    override suspend fun removeFromFavorites(filmId: Long) {
        filmDao.deleteFavoriteFilms(filmId)
    }

    private fun generateRandomRating(): Double {
        return (50..90).random() / 10.0
    }

}