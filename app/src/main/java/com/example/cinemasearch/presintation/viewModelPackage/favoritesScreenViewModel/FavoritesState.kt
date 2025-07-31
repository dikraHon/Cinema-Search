package com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel

import com.example.cinemasearch.domain.Films

data class FavoritesState(
    val films: List<Films> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
