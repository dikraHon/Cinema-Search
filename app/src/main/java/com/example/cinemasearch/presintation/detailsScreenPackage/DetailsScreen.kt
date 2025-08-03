@file:Suppress("DEPRECATION")

package com.example.cinemasearch.presintation.detailsScreenPackage

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
    val context = LocalContext.current
    LaunchedEffect(filmId) {
        detailsViewModel.loadFilmDetails(filmId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(films?.name ?: "Details")
                        },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    films?.let {
                        IconButton(onClick = {
                            val shareText = "Films: ${it.name ?: "Not have name"}\n" +
                                    "Rating: ${"%.1f".format(it.rating)}\n" +
                                    "Year: ${it.year}\n" +
                                    "Description: ${it.description?.take(200) ?: "Not have Description"}"
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            ContextCompat.startActivity(
                                context,
                                Intent.createChooser(intent, "Shared film"),
                                null
                            )
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Shared")
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else if (films != null) {
                FilmDetailsContent(
                    film = films!!,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(top = 0.dp)
                )
            }
        }
    )
}