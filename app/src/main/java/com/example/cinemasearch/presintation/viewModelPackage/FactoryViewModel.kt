package com.example.cinemasearch.presintation.viewModelPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class FactoryViewModel @Inject constructor(
    myViewModel: Provider<SearchFilmsViewModel>
) : ViewModelProvider.Factory {

    private val provides = mapOf<
            Class<*>,
            Provider<out ViewModel>
            >(
        SearchFilmsViewModel::class.java to myViewModel
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provides[modelClass]!!.get() as T
    }
}