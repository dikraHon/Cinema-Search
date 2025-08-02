package com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel

import com.example.cinemasearch.domain.Films

sealed class CollectionsUiState {
    object Loading : CollectionsUiState()
    data class Success(val films: List<Films>) : CollectionsUiState()
    data class Error(val message: String) : CollectionsUiState()
}