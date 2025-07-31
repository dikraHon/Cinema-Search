package com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _favoritesState = mutableStateOf(FavoritesState())
    val favoritesState: State<FavoritesState> = _favoritesState

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favoritesState.value = _favoritesState.value.copy(isLoading = true)
            try {
                val favorites = repository.getFavoritesFilms()
                _favoritesState.value = _favoritesState.value.copy(
                    films = favorites,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _favoritesState.value = _favoritesState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun toggleFavorites(films: Films) {
        viewModelScope.launch {
            try {
                if (films.isFavorite) {
                    repository.removeFromFavorites(films.id)
                } else {
                    repository.addToFavorites(films)
                }
                loadFavorites()
            } catch (e: java.lang.Exception) {
                _favoritesState.value = _favoritesState.value.copy(
                    error = e.message ?: "Failed to update favorites"
                )
            }
        }
    }
}