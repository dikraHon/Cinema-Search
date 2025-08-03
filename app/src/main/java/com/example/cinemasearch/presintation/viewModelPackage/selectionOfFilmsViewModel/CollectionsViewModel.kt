package com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.CollectionFilms
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _collections = MutableStateFlow<List<CollectionFilms>>(emptyList())
    val collections: StateFlow<List<CollectionFilms>> = _collections
    private val _filmsInCollection = MutableStateFlow<List<Films>>(emptyList())
    val filmsInCollection: StateFlow<List<Films>> = _filmsInCollection
    private val _selectedFilmId = MutableStateFlow<Long?>(null)
    val selectedFilmId: StateFlow<Long?> = _selectedFilmId

    init {
        loadCollections()
    }
    fun loadFilmsInCollection(collectionId: Long) {
        viewModelScope.launch {
            _filmsInCollection.value = repository.getFilmsInCollection(collectionId)
        }
    }
    fun addSelectedFilmToCollection(collectionId: Long) {
        viewModelScope.launch {
            repository.addFilmToCollection(selectedFilmId.value!!, collectionId)
            _filmsInCollection.value = repository.getFilmsInCollection(collectionId)
        }
    }
    fun refreshCollections() {
        viewModelScope.launch {
            _collections.value = repository.getAllCollections()
        }
    }

    fun loadCollections() {
        viewModelScope.launch {
            _collections.value = repository.getAllCollections()
        }
    }

    fun selectFilmForCollection(filmId: Long) {
        _selectedFilmId.value = filmId
    }

    fun createCollection(name: String) {
        viewModelScope.launch {
            repository.createCollection(name)
            loadCollections()
        }
    }
}