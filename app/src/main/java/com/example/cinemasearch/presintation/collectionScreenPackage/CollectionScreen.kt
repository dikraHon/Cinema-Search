package com.example.cinemasearch.presintation.collectionScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.presintation.mainScreenListFilms.FilmCard
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@Composable
fun CollectionScreen(
    collectionName: String,
    viewModel: CollectionsViewModel,
    favoritesViewModel: FavoritesViewModel,
    onFilmClick: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(collectionName) {
        viewModel.loadCollectionFilms(collectionName)
    }

    val films by viewModel.collectionFilms.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = collectionName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            films.isEmpty() -> {
                Text(
                    text = "No films in this collection",
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                LazyColumn {
                    items(films) { film ->
                        FilmCard(
                            film = film,
                            isFavorite = favoritesViewModel.favoriteFilms.any { it.id == film.id },
                            onFilmClick = { onFilmClick(film.id) },
                            onFavoriteClick = {
                                if (favoritesViewModel.favoriteFilms.any { it.id == film.id }) {
                                    favoritesViewModel.removeFromFavorites(film.id)
                                } else {
                                    favoritesViewModel.addToFavorites(film)
                                }
                            },
                            onCollectionClick = null // Не показываем кнопку добавления в коллекцию на экране коллекции
                        )
                    }
                }
            }
        }
    }
}