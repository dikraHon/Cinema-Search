package com.example.cinemasearch.presintation.collectionScreenPackage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cinemasearch.presintation.mainScreenListFilms.FilmCard
import com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailsScreen(
    collectionId: Long,
    collectionsViewModel: CollectionsViewModel,
    onBack: () -> Unit
) {
    val films by collectionsViewModel.filmsInCollection.collectAsState()

    LaunchedEffect(collectionId) {
        collectionsViewModel.loadFilmsInCollection(collectionId)
        collectionsViewModel.collections.collect {
            collectionsViewModel.loadFilmsInCollection(collectionId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Collection Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (films.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No films in this collection")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(films) { film ->
                    FilmCard(
                        film = film,
                        isFavorite = film.isFavorite,
                        onFilmClick = { },
                        onFavoriteClick = { },
                        onAddToCollection = {}
                    )
                }
            }
        }
    }
}