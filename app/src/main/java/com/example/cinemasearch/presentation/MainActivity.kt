package com.example.cinemasearch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cinemasearch.di.MyApp
import com.example.cinemasearch.presentation.mainScreenListFilms.MainScreen
import com.example.cinemasearch.presentation.viewModelPackage.FactoryViewModel
import com.example.cinemasearch.presentation.viewModelPackage.detailsViewModelPack.DetailsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel
import com.example.cinemasearch.ui.theme.CinemaSearchTheme

import javax.inject.Inject
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: FactoryViewModel
    private val viewModel: SearchFilmsViewModel by viewModels { viewModelFactory }
    private val favoritesViewModel: FavoritesViewModel by viewModels { viewModelFactory }
    private val detailsViewModel: DetailsViewModel by viewModels { viewModelFactory }

    private val collectionsViewModel: CollectionsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            CinemaSearchTheme {
                MainScreen(
                    viewModel = viewModel,
                    favoritesViewModel = favoritesViewModel,
                    detailsViewModel = detailsViewModel,
                    collectionsViewModel = collectionsViewModel
                )
            }
        }
    }
}