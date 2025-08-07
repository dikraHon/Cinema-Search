package com.example.cinemasearch.domain.repositoryPackage

import com.example.cinemasearch.domain.modelData.CollectionFilms
import com.example.cinemasearch.domain.modelData.Films

interface CollectionRepository {
    suspend fun getAllCollections(): List<CollectionFilms>
    suspend fun createCollection(name: String): Long
    suspend fun addFilmToCollection(filmId: Long, collectionId: Long)
    suspend fun getFilmsInCollection(collectionId: Long): List<Films>
}