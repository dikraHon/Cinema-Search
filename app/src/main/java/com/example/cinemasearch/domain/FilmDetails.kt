package com.example.cinemasearch.domain

data class FilmDetails(
    val id: Long,
    val title: String,
    val description: String,
    val posterUrl: String?,
    val rating: Double,
    val year: Int,
    val genres: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
