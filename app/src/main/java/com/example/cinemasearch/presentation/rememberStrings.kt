package com.example.cinemasearch.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cinemasearch.R

@Composable
fun rememberStrings(): Strings {
    return Strings(
        film = stringResource(R.string.film),
        notHaveName = stringResource(R.string.no_name),
        rating = stringResource(R.string.rating),
        year = stringResource(R.string.year_na),
        shareFilm = stringResource(R.string.share_film),
        noPoster = stringResource(R.string.no_poster),
        noTitle = stringResource(R.string.no_title),
        favorites = stringResource(R.string.favorite),
        addToCollection = stringResource(R.string.add_to_collection),
        selectSelection = stringResource(R.string.select_a_selection),
        cancel = stringResource(R.string.cancel),
        retry = stringResource(R.string.retry),
        noMovie = stringResource(R.string.no_movie),
        settingsScreen = stringResource(R.string.settings_screen),
        collectionsDetails = stringResource(R.string.collection_details),
        share = stringResource(R.string.share),
        back = stringResource(R.string.back),
        movieSelection = stringResource(R.string.movie_selection),
        shareSelection = stringResource(R.string.shared_selection),
        noFilmsInThisCollection = stringResource(R.string.no_films_in_this_collection),
        create = stringResource(R.string.create),
        createNewCollection = stringResource(R.string.create_new_collection),
        collectName = stringResource(R.string.collection_name),
        details = stringResource(R.string.details),
        description = stringResource(R.string.description),
        notHaveDescription = stringResource(R.string.not_have_description),
        films = stringResource(R.string.films),
        cinemaSearch = stringResource(R.string.cinema_search),
        collections = stringResource(R.string.collections),
        settings = stringResource(R.string.settings),
        menu = stringResource(R.string.menu),
        searchMovie = stringResource(R.string.search_movie),
        error = stringResource(R.string.free_token_expired)
    )
}