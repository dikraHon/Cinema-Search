package com.example.cinemasearch.presintation.mainScreenListFilms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinemasearch.presintation.favoriteScreenPackage.FavoritesScreen
import com.example.cinemasearch.presintation.menuFilmsPackage.DrawerContent
import com.example.cinemasearch.presintation.searchPackage.SearchTopBar
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presintation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: SearchFilmsViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    var searchQuery by remember { mutableStateOf("") }
    val debouncedSearchQuery = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(searchQuery) {
        delay(300)
        debouncedSearchQuery.value = searchQuery
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = "Retry"
                )
                viewModel.clearError()
            }
        }
    }

    LaunchedEffect(debouncedSearchQuery.value) {
        if (debouncedSearchQuery.value.length > 2) {
            viewModel.searchFilms(debouncedSearchQuery.value)
        } else if (debouncedSearchQuery.value.isEmpty()) {
            viewModel.loadFilms()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadFilms()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemSelected = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route)
                },
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                SearchTopBar(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onFavoritesClick = { navController.navigate("favorites") }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "films",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("films") {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        state.films.isNotEmpty() -> {
                            FilmsList(
                                films = state.films,
                                onRetry = { viewModel.loadFilms() },
                                onMenuClick = { scope.launch { drawerState.open() } },
                                onFavoriteClick = { film -> favoritesViewModel.toggleFavorites(films = film) },
                                onFilmClick = { filmId ->
                                    // Навигация к деталям фильма
                                    navController.navigate(
                                        "filmDetails/$filmId"
                                    )
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {
                            // Показать пустое состояние или сообщение об ошибке
                        }
                    }
                }
                // возможно надо изменить
                composable("favorites") {
                    FavoritesScreen(
                        viewModel = favoritesViewModel,
                        onFilmClick = { filmId ->
                        }
                    )
                }
                composable("settings") {
                    // Здесь будет экран настроек
                    Text("Settings Screen")
                }
            }
        }
    }
}
