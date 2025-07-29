package com.example.cinemasearch.presintation.adapterFilms

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
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
    films: List<Films>,
    onRetry: () -> Unit
) {
    LazyColumn {
        items(films) { film ->
            FilmCard(film = film)
        }
    }
}

@Composable
fun FilmCard(film: Films) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Безопасная загрузка изображения
            film.poster?.let { url ->
                AsyncImage(
                    model = film.poster,
                    contentDescription = film.name,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    placeholder = painterResource(R.drawable.place_holder),
                    error = painterResource(R.drawable.place_holder)
                )
            } ?: Image(
                painter = painterResource(R.drawable.place_holder),
                contentDescription = "No poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                Text(
                    text = film.name ?: "Без названия",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = film.description ?: "Нет описания",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "rating: ${film.rating.toString()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}