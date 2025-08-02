package com.example.cinemasearch.presintation.detailsScreenPackage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemasearch.presintation.viewModelPackage.detailsViewModelPack.DetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    filmId: Long,
    detailsViewModel: DetailsViewModel,
    onBackClick: () -> Unit
) {
    val films by detailsViewModel.film.collectAsStateWithLifecycle()
    val isLoading by detailsViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(filmId) {
        detailsViewModel.loadFilmDetails(filmId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(films?.name ?: "Details") },
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
        } else if (films != null) {
            FilmDetailsContent(film = films!!, modifier = Modifier.padding(padding))
        }
    }
}