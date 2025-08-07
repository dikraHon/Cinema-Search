package com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.domain.repositoryPackage.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    private val _favoriteFilms = MutableStateFlow<List<Films>>(emptyList())
    val favoriteFilms: List<Films> get() = _favoriteFilms.value

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _favoriteFilms.value = favoriteRepository.getFavoritesFilms()
        }
    }

    fun addToFavorites(film: Films) {
        viewModelScope.launch {
            favoriteRepository.addToFavorites(film)
            _favoriteFilms.value = _favoriteFilms.value + film
        }
    }

    fun removeFromFavorites(filmId: Long) {
        viewModelScope.launch {
            favoriteRepository.removeFromFavorites(filmId)
            _favoriteFilms.value = _favoriteFilms.value.filter { it.id != filmId }
        }
    }
}