package com.example.cinemasearch.presintation.viewModelPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presintation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import javax.inject.Inject
import javax.inject.Provider

class FactoryViewModel @Inject constructor(
    private val searchFilmsViewModelProvider: Provider<SearchFilmsViewModel>,
    private val favoritesViewModelProvider: Provider<FavoritesViewModel>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchFilmsViewModel::class.java) -> {
                searchFilmsViewModelProvider.get() as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                favoritesViewModelProvider.get() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}