package com.example.cinemasearch.data

import android.util.Log
import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.data.remote.CinemaSearchApi
import com.example.cinemasearch.di.ApiService
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApiService private val apiService: CinemaSearchApi,
    private val getDao: AppDatabase
) : Repository {
    private val apiKey = "NKV70XT-CPS4C16-HRTA6JW-ET0EVFT"
    private val filmDao = getDao.filmsDao()

    override suspend fun addFilmById(id: Long): Films {
        val filmsItem = Films(id = id, "asd", "asd", "" , 1.0)
        return apiService.addFilm(filmsItem)
    }

    override suspend fun deleteFilmById(id: Long) {
        filmDao.deleteFilmById(id)
    }

    override suspend fun getAllFilms(): List<Films> {
        return try {
            val response = apiService.getFilms(apiKey)
            response.docs.map { filmDto ->
                val film = Films(
                    id = filmDto.id,
                    name = filmDto.name,
                    description = filmDto.description,
                    poster = filmDto.poster?.url,
                    rating = filmDto.rating?.kp
                )
                Log.d("IMAGE_URL", "Poster URL: ${film.getFullPosterUrl()}")
                film
            }
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> throw Exception("Некорректный запрос. Проверьте параметры")
                401 -> throw Exception("Неверный API ключ")
                429 -> throw Exception("Превышен лимит запросов")
                else -> throw e
            }
        }
    }

    // Аналогично обновить другие методы

    override suspend fun getFilmById(id: Long): Films {
        return try {
            val response = apiService.getFilmById(apiKey, id)
            filmDao.insertFilms(response)
            response
        } catch (e: Exception) {
            filmDao.getFilmById(id) ?: throw e
        }
    }

/*    private suspend fun validateApiKey() {
        try {
            val testResponse = apiService.getFilms(apiKey, 1, 1, listOf("id"))
            if (testResponse.docs.isEmpty()) throw Exception("Invalid API response")
        } catch (e: Exception) {
            throw Exception("API key validation failed: ${e.message}")
        }
    }*/
}