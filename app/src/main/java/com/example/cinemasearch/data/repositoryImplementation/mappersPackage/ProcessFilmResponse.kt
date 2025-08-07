package com.example.cinemasearch.data.repositoryImplementation.mappersPackage

import com.example.cinemasearch.domain.modelData.Films

class ProcessFilmResponse() {
    fun processFilmResponse(films: List<FilmDTO>): List<Films> {
        return films.map { filmDto ->
            val imdbRating = filmDto.rating?.imdb ?: 0.0
            val rating = if (imdbRating == 0.0) {
                generateRandomRating()
            } else {
                imdbRating
            }
            Films(
                id = filmDto.id,
                name = filmDto.name ?: filmDto.alternativeName ?: "No name",
                description = filmDto.description ?: "No description",
                poster = filmDto.poster?.url ?: "",
                rating = rating,
                year = filmDto.year ?: 0,
                countries = filmDto.countries?.joinToString(", ") { it.name },
                genres = filmDto.genres.map { it.name }
            )
        }
    }

    fun generateRandomRating(): Double {
        return (50..90).random() / 10.0
    }

}