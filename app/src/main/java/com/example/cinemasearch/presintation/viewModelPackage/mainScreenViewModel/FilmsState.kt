package com.example.cinemasearch.presintation.viewModelPackage.mainScreenViewModel

import com.example.cinemasearch.domain.Films

data class FilmsState(
    val films: List<Films> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val category: FilmCategory = FilmCategory.POPULAR
)
