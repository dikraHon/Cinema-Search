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
            response.docs.map { filmDto ->
                val imdbRating = filmDto.rating?.imdb ?: 0.0
                val rating = if (imdbRating == 0.0) {
                    generateRandomRating()
                } else {
                    imdbRating
                }
                val film = Films(
                    id = filmDto.id,
                    name = filmDto.name ?: "no name",
                    description = filmDto.description ?: "no description",
                    poster = filmDto.poster?.url ?: "",
                    rating = rating,
                    year = filmDto.year ?: 0
                )
                film
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun searchFilms(query: String): List<Films> {
        return try {
            val response = apiService.searchFilms(apiKey, query)
            response.docs.map { filmDto ->
                val film = Films(
                    id = filmDto.id,
                    name = filmDto.name ?: "no name",
                    description = filmDto.description ?: "no description",
                    poster = filmDto.poster?.url ?: "",
                    rating = filmDto.rating?.imdb,
                    year = filmDto.year ?: 0
                )
                film
            }
        } catch (e: Exception){
            emptyList()
        }
    }

    // Аналогично обновить другие методы

    override suspend fun addFilmById(id: Long): Films {
        val filmsItem = Films(id = id, "asd", "asd", "", 1.0, 2001)
        return apiService.addFilm(filmsItem)
    }

    override suspend fun deleteFilmById(id: Long) {
        filmDao.deleteFilmById(id)
    }

    override suspend fun getFilmById(id: Long): Films {
        return try {
            val response = apiService.getFilmById(apiKey, id)
            filmDao.insertFilms(response)
            response
        } catch (e: Exception) {
            filmDao.getFilmById(id) ?: throw e
        }
    }

    private fun generateRandomRating(): Double {
        return (50..90).random() / 10.0
    }

}