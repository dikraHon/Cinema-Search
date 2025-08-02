package com.example.cinemasearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cinemasearch.domain.CollectionFilms
import com.example.cinemasearch.domain.FilmCollectionCrossRef
import com.example.cinemasearch.domain.Films

@Dao
interface CollectionsDao {
    @Insert
    suspend fun insertCollection(collection: CollectionFilms): Long
    @Insert
    suspend fun insertFilmCollectionCrossRef(crossRef: FilmCollectionCrossRef)
    @Query("SELECT * FROM collections")
    suspend fun getAllCollections(): List<CollectionFilms>

    @Insert
    suspend fun insertFilmToCollection(crossRef: FilmCollectionCrossRef)

    @Query("""
    SELECT * FROM films 
    WHERE id IN (
        SELECT filmId FROM film_collection_xref 
        WHERE collectionId = :collectionId
    )
""")
    suspend fun getFilmsInCollection(collectionId: Long): List<Films>
    @Query("""
    SELECT EXISTS(
        SELECT 1 FROM film_collection_xref 
        WHERE filmId = :filmId AND collectionId = :collectionId
    )
""")
    suspend fun doesFilmExistInCollection(filmId: Long, collectionId: Long): Boolean
}