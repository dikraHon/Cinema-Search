package com.example.cinemasearch.domain.useKlasses

import com.example.cinemasearch.domain.Repository

class UseDeleteFilmById(private val repository: Repository) {

    suspend operator fun invoke(id: Long) = repository.deleteFilmById(id)

}