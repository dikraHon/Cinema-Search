package com.example.cinemasearch.presintation.viewModelPackage.mainScreenViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
class SearchFilmsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(FilmsState())
    val state = _state.asStateFlow()

    fun loadFilms() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val films = repository.getAllFilms()
                _state.update {
                    it.copy(
                        films = films,
                        isLoading = false,
                        error = if (films.isEmpty()) "no data" else null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "error: ${e.message ?: "unknown error"}"
                    )
                }
            }
        }
    }
    //исправить возможно
    fun searchFilms(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val films = repository.searchFilms(query)
                _state.update {
                    it.copy(
                        films = films,
                        isLoading = false,
                        error = null
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
}
