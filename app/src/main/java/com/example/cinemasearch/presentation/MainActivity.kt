@file:Suppress("DEPRECATION")

package com.example.cinemasearch.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.cinemasearch.R
import com.example.cinemasearch.di.MyApp
import com.example.cinemasearch.presentation.mainScreenListFilms.MainScreen
import com.example.cinemasearch.presentation.settingsPackage.LocalizationManager
import com.example.cinemasearch.presentation.settingsPackage.ThemeViewModel
import com.example.cinemasearch.presentation.viewModelPackage.FactoryViewModel
import com.example.cinemasearch.presentation.viewModelPackage.detailsViewModelPack.DetailsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel
import com.example.cinemasearch.ui.theme.CinemaSearchTheme

import javax.inject.Inject

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: FactoryViewModel
    private val viewModel: SearchFilmsViewModel by viewModels { viewModelFactory }
    private val favoritesViewModel: FavoritesViewModel by viewModels { viewModelFactory }
    private val detailsViewModel: DetailsViewModel by viewModels { viewModelFactory }

    private val collectionsViewModel: CollectionsViewModel by viewModels { viewModelFactory }

    private val themeViewModel: ThemeViewModel by viewModels { viewModelFactory }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            LocalizationManager.setAppLocale(
                newBase,
                LocalizationManager.getCurrentLanguage(newBase!!)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        setAppTheme()
        super.onCreate(savedInstanceState)

        setContent {
            CinemaSearchTheme(darkTheme = themeViewModel.isDarkTheme) {
                MainScreen(
                    viewModel = viewModel,
                    favoritesViewModel = favoritesViewModel,
                    detailsViewModel = detailsViewModel,
                    collectionsViewModel = collectionsViewModel,
                    themeViewModel = themeViewModel
                )
            }
        }
    }

    private fun setAppTheme() {
        if (themeViewModel.isDarkTheme) {
            setTheme(R.style.Theme_CinemaSearch_Dark)
            window.setDarkSystemBars()
        } else {
            setTheme(R.style.Theme_CinemaSearch)
            window.setLightSystemBars()
        }
    }

}



fun Window.setDarkSystemBars() {
    statusBarColor = Color.Black.toArgb()
    navigationBarColor = Color.Black.toArgb()
    decorView.systemUiVisibility = 0
}

fun Window.setLightSystemBars() {
    statusBarColor = Color.White.toArgb()
    navigationBarColor = Color.White.toArgb()
    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
}