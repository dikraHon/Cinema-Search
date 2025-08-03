package com.example.cinemasearch.presintation.detailsScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinemasearch.domain.Films

@Composable
fun FilmDetailsContent(film: Films, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp) // Только горизонтальные отступы
    ) {
        AsyncImage(
            model = film.poster,
            contentDescription = film.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 0.dp) // Нет отступа сверху
        )

        Spacer(modifier = Modifier.height(8.dp)) // Уменьшенный отступ после постера

        Text(
            text = film.name ?: "No title",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 4.dp) // Минимальный отступ
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
        Spacer(modifier = Modifier.height(32.dp))
    }
}