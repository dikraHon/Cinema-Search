package com.example.cinemasearch.domain.useKlasses

import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository

class UseAddFilmById(private val repository: Repository) {

    suspend operator fun invoke(id: Long) = repository.addFilmById(id)

}