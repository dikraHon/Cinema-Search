package com.example.cinemasearch.presintation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.data.repository.RepositoryImpl
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class FilmsState(
    val films: List<Films> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class SearchFilmsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(FilmsState())
    val filmsState = _state.asStateFlow()

    fun loadFilms() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val films = repository.getAllFilms()
                _state.update {
                    it.copy(
                        films = films,
                        isLoading = false,
                        error = if (films.isEmpty()) "Нет данных" else null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка: ${e.message ?: "неизвестная ошибка"}"
                    )
                }
            }
        }
    }
    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
