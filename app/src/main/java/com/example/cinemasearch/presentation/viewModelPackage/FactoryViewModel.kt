package com.example.cinemasearch.presentation.viewModelPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemasearch.presentation.viewModelPackage.themeViewModelPack.ThemeViewModel
import com.example.cinemasearch.presentation.viewModelPackage.detailsViewModelPack.DetailsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel
import javax.inject.Inject
import javax.inject.Provider

class FactoryViewModel @Inject constructor(
    private val searchFilmsViewModelProvider: Provider<SearchFilmsViewModel>,
    private val favoritesViewModelProvider: Provider<FavoritesViewModel>,
    private val detailsViewModelProvider: Provider<DetailsViewModel>,
    private val collectionsViewModelProvider: Provider<CollectionsViewModel>,
    private val themeViewModelProvider: Provider<ThemeViewModel>,
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
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                detailsViewModelProvider.get() as T
            }
            modelClass.isAssignableFrom(CollectionsViewModel::class.java) -> {
                collectionsViewModelProvider.get() as T
            }
            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> {
                themeViewModelProvider.get() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}