@file:Suppress("DEPRECATION")

package com.example.cinemasearch.presentation.collectionScreenPackage

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.cinemasearch.presentation.mainScreenListFilms.FilmCard
import com.example.cinemasearch.presentation.rememberStrings
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailsScreen(
    collectionId: Long,
    collectionsViewModel: CollectionsViewModel,
    onBack: () -> Unit
) {
    val films by collectionsViewModel.filmsInCollection.collectAsState()
    val context = LocalContext.current
    val string = rememberStrings()
    LaunchedEffect(collectionId) {
        collectionsViewModel.loadFilmsInCollection(collectionId)
        collectionsViewModel.collections.collect {
            collectionsViewModel.loadFilmsInCollection(collectionId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(string.collectionsDetails) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = string.back)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val filmList = films.joinToString("\n• ") { it.name ?: string.noTitle }
                        val shareText = "${string.movieSelection}:\n\n• $filmList"

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        ContextCompat.startActivity(
                            context,
                            Intent.createChooser(intent, string.shareSelection),
                            null
                        )
                    }) {
                        Icon(Icons.Default.Share, contentDescription = string.share)
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
                Text(
                    text = string.noFilmsInThisCollection,
                    color = MaterialTheme.colorScheme.onSurface
                )
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