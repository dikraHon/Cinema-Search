package com.example.cinemasearch.presintation.mainScreenListFilms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.cinemasearch.R
import com.example.cinemasearch.domain.Films

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsList(
    films: List<Films>,
    isFavoriteList: List<Long>, // Список ID избранных фильмов
    onFavoriteClick: (Films) -> Unit,
    onRetry: () -> Unit,
    onMenuClick: () -> Unit,
    onFilmClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TopAppBar(
            title = { Text("Films") },
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = "Menu"
                    )
                }
            },
        )
        LazyColumn {
            items(films, key = { it.id }) { film ->
                FilmCard(
                    film = film,
                    isFavorite = isFavoriteList.contains(film.id), // Передаем состояние
                    onFilmClick = { onFilmClick(film.id) },
                    onFavoriteClick = { onFavoriteClick(film) } // Пробрасываем колбэк
                )
            }
        }
    }
}