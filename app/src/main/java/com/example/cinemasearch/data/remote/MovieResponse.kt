package com.example.cinemasearch.data.remote

import com.example.cinemasearch.domain.Films

data class MovieResponse(
    val docs: List<FilmDTO>
)

data class FilmDTO(
    val id: Long,
    val name: String?,
    val alternativeName: String?,
    val description: String?,
    val poster: PosterDTO?,
    val rating: RatingDTO?,
    val year: Int?,
    val genres: List<GenreDTO>
) {
    data class PosterDTO(val url: String?)
    data class RatingDTO(val kp: Double?)
    data class GenreDTO(val name: String)
}