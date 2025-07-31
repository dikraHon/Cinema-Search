package com.example.cinemasearch.data.repository

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
    data class RatingDTO(
        val kp: Double?,
        val imdb: Double?,
        val filmCritics: Double?,
        val russianFilmCritics: Double?,
        val await: Double?
    )
    data class GenreDTO(val name: String)
}