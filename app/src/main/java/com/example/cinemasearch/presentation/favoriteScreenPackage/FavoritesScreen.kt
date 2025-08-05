package com.example.cinemasearch.presentation.favoriteScreenPackage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.R
import com.example.cinemasearch.domain.modelData.Films
import com.example.cinemasearch.presentation.mainScreenListFilms.FilmCard
import com.example.cinemasearch.presentation.componentsPack.rememberStrings
import com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel,
    collectionsViewModel: CollectionsViewModel,
    modifier: Modifier = Modifier
) {
    val favorites = favoritesViewModel.favoriteFilms
    val string = rememberStrings()
    val collections by collectionsViewModel.collections.collectAsState()
    var showAddToCollection by remember { mutableStateOf(false) }
    var selectedFilmId by remember { mutableStateOf<Long?>(null) }

    // Устанавливаем выбранный фильм при изменении selectedFilmId
    LaunchedEffect(selectedFilmId) {
        selectedFilmId?.let { collectionsViewModel.selectFilmForCollection(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.my_bag),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.9f
        )
        if (favorites.isEmpty()) {
            Text(
                text = string.noMovie,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            LazyColumn(modifier = modifier) {
                items(favorites, key = { it.id }) { film ->
                    FilmCard(
                        film = film,
                        isFavorite = true,
                        onFilmClick = { },
                        onFavoriteClick = { favoritesViewModel.removeFromFavorites(film.id) },
                        onAddToCollection = {
                            selectedFilmId = film.id
                            showAddToCollection = true
                        }
                    )
                }
            }
        }
    }

    if (showAddToCollection) {
        AlertDialog(
            onDismissRequest = { showAddToCollection = false },
            title = { Text("Добавить в коллекцию") },
            text = {
                Column {
                    Text("Выберите коллекцию:")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(modifier = Modifier.height(200.dp)) {
                        items(collections) { collection ->
                            TextButton(
                                onClick = {
                                    collectionsViewModel.addSelectedFilmToCollection(collection.id)
                                    showAddToCollection = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(collection.name)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showAddToCollection = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}