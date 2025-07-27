package com.example.cinemasearch.domain.useKlasses

import com.example.cinemasearch.domain.Repository

class UseGetAllFilms(private val repository: Repository){

    suspend operator fun invoke() = repository.getAllFilms()

}