package com.example.cinemasearch.presintation.detailsScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.presintation.viewModelPackage.detailsViewModelPack.DetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    filmId: Long,
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit
) {
    val film by viewModel.film.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(filmId) {
        viewModel.loadFilmDetails(filmId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(film?.name ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else if (film != null) {
            FilmDetailsContent(film = film!!, modifier = Modifier.padding(padding))
        }
    }
}

@Composable
fun FilmDetailsContent(film: Films, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        AsyncImage(
            model = film.poster,
            contentDescription = film.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = film.name ?: "No title",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Rating: ${"%.1f".format(film.rating)}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Year: ${film.year}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = film.description ?: "No description",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}