package com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CollectionsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _collectionFilms = MutableStateFlow<List<Films>>(emptyList())
    val collectionFilms: StateFlow<List<Films>> = _collectionFilms

    private val _collections = MutableStateFlow<List<String>>(emptyList())
    val collections: StateFlow<List<String>> = _collections

    init {
        loadCollections()
    }

    fun loadCollectionFilms(collectionName: String) {
        viewModelScope.launch {
            _collectionFilms.value = repository.getCollection(collectionName)
        }
    }

    fun createCollection(name: String) {
        viewModelScope.launch {
            repository.createCollection(name)
            loadCollections()
        }
    }

    fun addToCollection(filmId: Long, collectionName: String) {
        viewModelScope.launch {
            repository.addFilmToCollection(filmId, collectionName)
            loadCollections()
        }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            _collections.value = repository.getAllCollections()
        }
    }
}