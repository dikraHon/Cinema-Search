package com.example.cinemasearch.presintation.detailsScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinemasearch.domain.Films

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