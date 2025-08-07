package com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasearch.domain.modelData.CollectionFilms
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.domain.repositoryPackage.CollectionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionsViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
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
            _filmsInCollection.value = collectionRepository.getFilmsInCollection(collectionId)
        }
    }
    fun addSelectedFilmToCollection(collectionId: Long) {
        viewModelScope.launch {
            collectionRepository.addFilmToCollection(selectedFilmId.value!!, collectionId)
            _filmsInCollection.value = collectionRepository.getFilmsInCollection(collectionId)
        }
    }
    fun refreshCollections() {
        viewModelScope.launch {
            _collections.value = collectionRepository.getAllCollections()
        }
    }

    fun loadCollections() {
        viewModelScope.launch {
            _collections.value = collectionRepository.getAllCollections()
        }
    }

    fun selectFilmForCollection(filmId: Long) {
        _selectedFilmId.value = filmId
    }

    fun createCollection(name: String) {
        viewModelScope.launch {
            collectionRepository.createCollection(name)
            loadCollections()
        }
    }
}