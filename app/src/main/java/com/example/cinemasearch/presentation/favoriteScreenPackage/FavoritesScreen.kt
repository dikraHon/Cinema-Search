package com.example.cinemasearch.presentation.favoriteScreenPackage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinemasearch.presentation.mainScreenListFilms.FilmCard
import com.example.cinemasearch.presentation.rememberStrings
import com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val favorites = favoritesViewModel.favoriteFilms
    val string = rememberStrings()

    if (favorites.isEmpty()) {
        Text(string.noMovie)
    } else {
        LazyColumn(modifier = modifier) {
            items(favorites, key = { it.id }) { film ->
                FilmCard(
                    film = film,
                    isFavorite = true,
                    onFilmClick = {  },
                    onFavoriteClick = { favoritesViewModel.removeFromFavorites(film.id) },
                    onAddToCollection = {}
                )
            }
        }
    }
}