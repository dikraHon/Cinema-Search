package com.example.cinemasearch.data.repositoryImplementation.reposiroryImplPackage

import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.data.remote.ApiConfig
import com.example.cinemasearch.data.remote.CinemaSearchApi
import com.example.cinemasearch.data.repositoryImplementation.mappersPackage.ProcessFilmResponse
import com.example.cinemasearch.di.qualifier.ApiService
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.domain.repositoryPackage.FilmRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class FilmRepositoryImpl @Inject constructor(
    @ApiService private val apiService: CinemaSearchApi,
    getDao: AppDatabase
) : FilmRepository {
    private val apiKey = ApiConfig.API_KEY
    private val filmDao = getDao.filmsDao()
    private val processFilmResponse = ProcessFilmResponse()

    override suspend fun getAllFilms(): List<Films> {
        return try {
            val response = apiService.getFilms(apiKey, limit = 50)
            processFilmResponse.processFilmResponse(response.docs)
        } catch (e: Exception) {
            throw Exception("Failed to load film details: $e")
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
            processFilmResponse.processFilmResponse(response.docs)
        } catch (e: Exception) {
            throw Exception("Failed to load film details: $e")
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
            processFilmResponse.processFilmResponse(response.docs)
        } catch (e: Exception) {
            throw Exception("Failed to load film details: $e")
        }
    }
    override suspend fun getNewReleases(): List<Films> {
        return try {
            val response = apiService.getFilms(
                apiKey = apiKey,
                sortField = "premiere.world",
                sortType = "-1",
                limit = 50
            )
            processFilmResponse.processFilmResponse(response.docs)
        } catch (e: Exception) {
            throw Exception("Failed to load film details: $e")
        }
    }

    override suspend fun searchFilms(query: String): List<Films> {
        return try {
            val response = apiService.searchFilms(apiKey, query)
            processFilmResponse.processFilmResponse(response.docs)
        } catch (e: Exception){
            throw Exception("Failed to load film details: $e")
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
                rating = if (imdbRating == 0.0) processFilmResponse.generateRandomRating() else imdbRating,
                year = response.year ?: 0,
                isFavorite = filmDao.isFavorite(response.id)
            )
        } catch (e: Exception) {
            throw Exception("Failed to load film details: $e")
        }
    }
}