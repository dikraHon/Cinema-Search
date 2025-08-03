package com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel

import com.example.cinemasearch.domain.modelData.Films

data class FilmsState(
    val films: List<Films> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val category: FilmCategory = FilmCategory.POPULAR
)
