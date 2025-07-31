package com.example.cinemasearch.presintation.favoriteScreenPackage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.presintation.mainScreenListFilms.FilmCard

@Composable
fun FavoritesList(
    films: List<Films>,
    onFilmClick: (Long) -> Unit,
    onToggleFavorite: (Films) -> Unit
) {
    LazyColumn{
        items(films) { film ->
            FilmCard(
                film = film,
                onFilmClick = {
                    onFilmClick(film.id)
                },
                onFavoriteClick = { onToggleFavorite(film) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}