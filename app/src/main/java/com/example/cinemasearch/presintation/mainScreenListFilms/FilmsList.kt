package com.example.cinemasearch.presintation.mainScreenListFilms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.domain.Films

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsList(
    films: List<Films>,
    isFavoriteList: List<Long>,
    onFavoriteClick: (Films) -> Unit,
    onFilmClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
        text = "Films",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(
            top = 24.dp,
            start = 16.dp,
            bottom = 12.dp
        )
    )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        LazyColumn {
            items(films, key = { it.id }) { film ->
                FilmCard(
                    film = film,
                    isFavorite = isFavoriteList.contains(film.id),
                    onFilmClick = { onFilmClick(film.id) },
                    onFavoriteClick = { onFavoriteClick(film) }
                )
            }
        }
    }
}