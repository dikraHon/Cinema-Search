package com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.repositoryPackage.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
class SearchFilmsViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(FilmsState())
    val state = _state.asStateFlow()

    private var currentCategory: FilmCategory = FilmCategory.POPULAR

    fun loadFilms(category: FilmCategory = FilmCategory.POPULAR) {
        currentCategory = category
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val films = when (category) {
                    FilmCategory.POPULAR -> filmRepository.getPopularFilms()
                    FilmCategory.TOP_RATED -> filmRepository.getTopRatedFilms()
                    FilmCategory.NEW -> filmRepository.getNewReleases()
                    else -> filmRepository.getAllFilms()
                }
                _state.update {
                    it.copy(
                        films = films,
                        isLoading = false,
                        error = if (films.isEmpty()) "No data available" else null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error: ${e.message ?: "Unknown error"}"
                    )
                }
            }
        }
    }

    fun searchFilms(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val films = filmRepository.searchFilms(query)
                _state.update {
                    it.copy(
                        films = films,
                        isLoading = false,
                        error = null,
                        category = FilmCategory.SEARCH
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun getCurrentCategory(): FilmCategory = currentCategory
}