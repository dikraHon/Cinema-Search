package com.example.cinemasearch.domain.useKlasses

import com.example.cinemasearch.domain.Repository

class UseGetFilmById(private val repository: Repository) {

    suspend operator fun invoke(id: Long) = repository.getFilmById(id)

}