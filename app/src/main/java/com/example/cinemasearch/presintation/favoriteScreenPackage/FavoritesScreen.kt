package com.example.cinemasearch.presintation.favoriteScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onFilmClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    val state = viewModel.favoritesState.value

    Column(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            state.films.isEmpty() -> EmptyFavoritesPlaceholder()
            else -> {
                FavoritesList(
                    films = state.films,
                    onFilmClick = onFilmClick,
                    onToggleFavorite = {
                        viewModel.toggleFavorites(films = it)
                    }
                )
            }
        }
    }
}