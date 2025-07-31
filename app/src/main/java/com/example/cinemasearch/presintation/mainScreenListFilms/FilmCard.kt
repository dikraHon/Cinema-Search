package com.example.cinemasearch.presintation.mainScreenListFilms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinemasearch.R
import com.example.cinemasearch.domain.Films

@Composable
fun FilmCard(
    film: Films,
    onFilmClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onFilmClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box(contentAlignment = Alignment.TopEnd) {
                // Безопасная загрузка изображения
                film.poster?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = film.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        placeholder = painterResource(R.drawable.place_holder),
                        error = painterResource(R.drawable.place_holder),
                        contentScale = ContentScale.Crop
                    )
                } ?: Image(
                    painter = painterResource(R.drawable.place_holder),
                    contentDescription = "No poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (film.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (film.isFavorite) Color.Red else Color.White
                    )
                }
            }

            Column(Modifier.padding(16.dp)) {
                Text(
                    text = film.name ?: "No name",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = film.description ?: "No description",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Rating: ${"%.1f".format(film.rating)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Year: ${film.year ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}