package com.example.cinemasearch.data.database.daoPackage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cinemasearch.domain.modelData.CollectionFilms
import com.example.cinemasearch.domain.modelData.FilmCollectionCrossRef
import com.example.cinemasearch.domain.modelData.Films

@Dao
interface CollectionsDao {
    @Insert
    suspend fun insertCollection(collection: CollectionFilms): Long
    @Insert
    suspend fun insertFilmCollectionCrossRef(crossRef: FilmCollectionCrossRef)
    @Query("SELECT * FROM collections")
    suspend fun getAllCollections(): List<CollectionFilms>

    @Query("""
    SELECT * FROM films 
    WHERE id IN (
        SELECT filmId FROM film_collection_xref 
        WHERE collectionId = :collectionId
    )
""")
    suspend fun getFilmsInCollection(collectionId: Long): List<Films>
}