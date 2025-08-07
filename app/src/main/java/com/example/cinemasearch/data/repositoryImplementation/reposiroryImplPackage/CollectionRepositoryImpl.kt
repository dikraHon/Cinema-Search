package com.example.cinemasearch.data.repositoryImplementation.reposiroryImplPackage

import com.example.cinemasearch.data.database.AppDatabase
import com.example.cinemasearch.data.remote.ApiConfig
import com.example.cinemasearch.data.remote.CinemaSearchApi
import com.example.cinemasearch.data.repositoryImplementation.mappersPackage.toFilmEntity
import com.example.cinemasearch.di.qualifier.ApiService
import com.example.cinemasearch.domain.modelData.CollectionFilms
import com.example.cinemasearch.domain.modelData.FilmCollectionCrossRef
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.domain.repositoryPackage.CollectionRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class CollectionRepositoryImpl @Inject constructor(
    @ApiService private val apiService: CinemaSearchApi,
    private val getDao: AppDatabase
) : CollectionRepository {
    private val apiKey = ApiConfig.API_KEY
    private val filmDao = getDao.filmsDao()
    private val collectionsDao = getDao.collectionsDao()
    private val favoritesDao = getDao.favoritesDao()

    override suspend fun getAllCollections(): List<CollectionFilms> {
        return getDao.collectionsDao().getAllCollections()
    }

    override suspend fun createCollection(name: String): Long {
        return getDao.collectionsDao().insertCollection(CollectionFilms(name = name))
    }

    override suspend fun addFilmToCollection(filmId: Long, collectionId: Long) {
        if (filmDao.checkFilmExists(filmId) == null) {
            val filmFromApi = apiService.getFilmById(apiKey, filmId)
            favoritesDao.insertFavoritesFilms(filmFromApi.toFilmEntity())
        }
        collectionsDao.insertFilmCollectionCrossRef(
            FilmCollectionCrossRef(
                filmId = filmId,
                collectionId = collectionId
            )
        )
    }

    override suspend fun getFilmsInCollection(collectionId: Long): List<Films> {
        return getDao.collectionsDao().getFilmsInCollection(collectionId)
    }
}