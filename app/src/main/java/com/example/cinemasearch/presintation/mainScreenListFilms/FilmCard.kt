package com.example.cinemasearch.presintation.mainScreenListFilms

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cinemasearch.R
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import kotlinx.coroutines.launch

@Composable
fun FilmCard(
    film: Films,
    isFavorite: Boolean, // Принимаем состояние извне
    onFilmClick: () -> Unit,
    onFavoriteClick: () -> Unit, // Колбэк для клика
    modifier: Modifier = Modifier
) {

    //var isFavorite by remember { mutableStateOf(false) }
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFavoriteClick() } // Клик на всю строку
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (isFavorite) "В избранном" else "Добавить в избранное",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}