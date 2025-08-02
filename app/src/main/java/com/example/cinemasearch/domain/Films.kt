package com.example.cinemasearch.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class Films(
    @PrimaryKey val id: Long,
    val name: String?,
    val description: String?,
    val poster: String?,
    val rating: Double?,
    val year: Int?,
    val isFavorite: Boolean = false,
    val collections: String = ""
)