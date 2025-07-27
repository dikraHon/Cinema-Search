package com.example.cinemasearch.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class Films(
    @PrimaryKey val id: Long,
    val name: String?,
    val description: String?,
    val poster: String?,
    val rating: Double?
) {
    // Функция для обработки разных форматов URL
    fun getFullPosterUrl(): String? {
        return when {
            poster.isNullOrBlank() -> null
            poster!!.startsWith("http") -> poster
            poster.startsWith("/") -> "https://image.tmdb.org/t/p/w500$poster"
            else -> "https://$poster" // Если вдруг без http
        }
    }
}