package com.example.cinemasearch.data.repositoryImplementation.mappersPackage

import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.presentation.rememberStrings

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
                name = filmDto.name ?: "no name",
                description = filmDto.description ?: "no description",
                poster = filmDto.poster?.url ?: "",
                rating = rating,
                year = filmDto.year ?: 0
            )
        }
    }

    fun generateRandomRating(): Double {
        return (50..90).random() / 10.0
    }

}