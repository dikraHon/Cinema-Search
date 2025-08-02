package com.example.cinemasearch.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_collection_xref")
data class FilmCollectionCrossRef(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val filmId: Long,
    val collectionId: Long
)