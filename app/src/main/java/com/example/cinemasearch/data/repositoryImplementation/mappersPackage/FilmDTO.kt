package com.example.cinemasearch.data.repositoryImplementation.mappersPackage

import com.example.cinemasearch.domain.modelData.Films

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

fun FilmDTO.toFilmEntity(): Films {
    return Films(
        id = this.id,
        name = this.name ?: "No name",
        description = this.description ?: "No description",
        poster = this.poster?.url ?: "",
        rating = this.rating?.imdb ?: 0.0,
        year = this.year ?: 0
    )
}