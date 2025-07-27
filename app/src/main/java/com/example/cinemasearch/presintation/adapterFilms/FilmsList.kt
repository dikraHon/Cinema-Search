package com.example.cinemasearch.presintation.adapterFilms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinemasearch.R
import com.example.cinemasearch.domain.Films

@Composable
fun FilmsList(
    modifier: Modifier = Modifier,
    films: List<Films>,
    onRetry: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(films) { film ->
            FilmsCard(film = film)
        }
    }
}

@Composable
fun FilmsCard(film: Films) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            AsyncImage(
                model = film.poster?.takeIf { it.isNotBlank() },
                contentDescription = film.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.place_holder),
                error = painterResource(R.drawable.place_holder),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = film.name ?: "Unknown")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = film.description ?: "No description")
            }
        }
    }
}