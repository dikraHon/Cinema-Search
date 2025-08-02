package com.example.cinemasearch.presintation.mainScreenListFilms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsList(
    films: List<Films>,
    isFavoriteList: List<Long>,
    onFavoriteClick: (Films) -> Unit,
    onFilmClick: (Long) -> Unit,
    collectionsViewModel: CollectionsViewModel,
    modifier: Modifier = Modifier
) {
    val collections by collectionsViewModel.collections.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = "Films",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                top = 24.dp,
                start = 16.dp,
                bottom = 12.dp
            )
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Select a selection") },
                text = {
                    Column {
                        collections.forEach { collection ->
                            Text(
                                text = collection.name,
                                modifier = Modifier
                                    .clickable {
                                        collectionsViewModel.addSelectedFilmToCollection(collection.id)
                                        showDialog = false
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        LazyColumn {
            items(films, key = { it.id }) { film ->
                FilmCard(
                    film = film,
                    isFavorite = isFavoriteList.contains(film.id),
                    onFilmClick = { onFilmClick(film.id) },
                    onFavoriteClick = { onFavoriteClick(film) },
                    onAddToCollection = {
                        collectionsViewModel.selectFilmForCollection(film.id)
                        showDialog = true
                        collectionsViewModel.refreshCollections()
                    }
                )
            }
        }
    }
}