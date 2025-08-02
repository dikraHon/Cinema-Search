package com.example.cinemasearch.data.repository

import android.util.Log
import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.di.ApiService
import com.example.cinemasearch.data.remote.ApiConfig
import com.example.cinemasearch.data.remote.CinemaSearchApi
import com.example.cinemasearch.domain.FilmDetails
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
            val response = apiService.getFilms(apiKey, limit = 50)
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
                limit = 50
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
                limit = 50
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
                limit = 50
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
    override suspend fun getFilmDetails(id: Long): Films {
        return try {
            val response = apiService.getFilmById(apiKey, id)
            val imdbRating = response.rating?.imdb ?: 0.0
            Films(
                id = response.id,
                name = response.name ?: "No name",
                description = response.description ?: "No description",
                poster = response.poster?.url ?: "",
                rating = if (imdbRating == 0.0) generateRandomRating() else imdbRating,
                year = response.year ?: 0,
                isFavorite = filmDao.isFavorite(response.id)
            )
        } catch (e: Exception) {
            throw Exception("Failed to load film details: $e")
        }
    }

    override suspend fun createCollection(name: String) {
        // Просто сохраняем название, коллекции будут появляться при добавлении фильмов
    }

    override suspend fun getAllCollections(): List<String> {
        return filmDao.getAllFilms()
            .flatMap { it.collections.split(",").map { it.trim() } }
            .filter { it.isNotEmpty() }
            .distinct()
    }

    override suspend fun addFilmToCollection(filmId: Long, collectionName: String) {
        val film = filmDao.getFilmById(filmId) ?: return
        val currentCollections = film.collections.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toMutableList()

        if (!currentCollections.contains(collectionName)) {
            currentCollections.add(collectionName)
            filmDao.updateFilmCollections(
                filmId,
                currentCollections.joinToString(",")
            )
        }
    }

    override suspend fun getCollection(collectionName: String): List<Films> {
        return filmDao.getFilmsInCollection(collectionName)
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