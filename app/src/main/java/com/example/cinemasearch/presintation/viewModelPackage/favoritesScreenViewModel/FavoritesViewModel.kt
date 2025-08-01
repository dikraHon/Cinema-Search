package com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _favoriteFilms = mutableStateOf<List<Films>>(emptyList())
    val favoriteFilms: List<Films> get() = _favoriteFilms.value

    fun addToFavorites(film: Films) {
        _favoriteFilms.value = _favoriteFilms.value + film
    }

    fun removeFromFavorites(filmId: Long) {
        _favoriteFilms.value = _favoriteFilms.value.filter { it.id != filmId }
    }


}