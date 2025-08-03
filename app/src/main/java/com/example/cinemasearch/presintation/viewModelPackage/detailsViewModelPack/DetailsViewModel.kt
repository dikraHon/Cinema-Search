package com.example.cinemasearch.presintation.viewModelPackage.detailsViewModelPack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.domain.repositoryPackage.FilmRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
) : ViewModel() {

    private val _film = MutableStateFlow<Films?>(null)
    val film = _film.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadFilmDetails(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _film.value = filmRepository.getFilmDetails(id)
            } catch (e: Exception) {
                e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}