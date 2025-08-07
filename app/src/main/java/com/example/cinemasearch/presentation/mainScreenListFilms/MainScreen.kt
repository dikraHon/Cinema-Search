package com.example.cinemasearch.presentation.mainScreenListFilms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinemasearch.presentation.collectionScreenPackage.CollectionDetailsScreen
import com.example.cinemasearch.presentation.collectionScreenPackage.CollectionsScreen
import com.example.cinemasearch.presentation.collectionScreenPackage.CreateCollectionScreen
import com.example.cinemasearch.presentation.detailsScreenPackage.DetailsScreen
import com.example.cinemasearch.presentation.favoriteScreenPackage.FavoritesScreen
import com.example.cinemasearch.presentation.menuFilmsPackage.DrawerContent
import com.example.cinemasearch.presentation.componentsPack.rememberStrings
import com.example.cinemasearch.presentation.searchPackage.CategoryChips
import com.example.cinemasearch.presentation.searchPackage.SearchTopBar
import com.example.cinemasearch.presentation.settingsPackage.SettingsScreen
import com.example.cinemasearch.presentation.viewModelPackage.themeViewModelPack.ThemeViewModel
import com.example.cinemasearch.presentation.viewModelPackage.detailsViewModelPack.DetailsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: SearchFilmsViewModel,
    favoritesViewModel: FavoritesViewModel,
    detailsViewModel: DetailsViewModel,
    collectionsViewModel: CollectionsViewModel,
    themeViewModel: ThemeViewModel
) {
    // 1. Navigation and UI state
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 2.Search Status
    var searchQuery by remember { mutableStateOf("") }

    //Loading Data
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val strings = rememberStrings()

    LaunchedEffect(searchQuery) {
        delay(500)
        if (searchQuery.length > 2) {
            viewModel.searchFilms(searchQuery)
        } else if (searchQuery.isEmpty()) {
            viewModel.loadFilms()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = strings.retry
                )
                viewModel.clearError()
            }
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
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        scrimColor = Color(0x66000000)
    ) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .statusBarsPadding()
                ) {
                    SearchTopBar(
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onFavoritesClick = { navController.navigate("favorites") }
                    )
                    if (navController.currentDestination?.route == "films") {
                        CategoryChips(
                            currentCategory = viewModel.getCurrentCategory(),
                            onCategorySelected = { category ->
                                viewModel.loadFilms(category)
                            }
                        )
                    }
                }
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
                                isFavoriteList = favoritesViewModel.favoriteFilms.map { it.id },
                                onFavoriteClick = { film ->
                                    if (favoritesViewModel.favoriteFilms.any { it.id == film.id }) {
                                        favoritesViewModel.removeFromFavorites(film.id)
                                    } else {
                                        favoritesViewModel.addToFavorites(film)
                                    }
                                },
                                onFilmClick = { filmId ->
                                    navController.navigate("details/$filmId")
                                },
                                collectionsViewModel = collectionsViewModel,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> { Text(
                                strings.noMovie,
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                composable("favorites") {
                    FavoritesScreen(
                        favoritesViewModel = favoritesViewModel,
                        collectionsViewModel = collectionsViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(
                    route = "details/{filmId}",
                    arguments = listOf(navArgument("filmId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val filmId = backStackEntry.arguments?.getLong("filmId") ?: 0L
                    DetailsScreen(
                        filmId = filmId,
                        detailsViewModel = detailsViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable("collections") {
                    CollectionsScreen(
                        viewModel = collectionsViewModel,
                        onCreateCollection = {
                            navController.navigate("createCollection")
                        },
                        onCollectionSelected = { collectionId ->
                            navController.navigate("collection/$collectionId")
                        }
                    )
                }

                composable("createCollection") {
                    CreateCollectionScreen(
                        viewModel = collectionsViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(
                    "collection/{collectionId}",
                    arguments = listOf(navArgument("collectionId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val collectionId = backStackEntry.arguments?.getLong("collectionId") ?: 0L
                    CollectionDetailsScreen(
                        collectionId = collectionId,
                        collectionsViewModel = collectionsViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("settings") {
                    SettingsScreen(
                        onBackClick = { navController.popBackStack() },
                        themeViewModel = themeViewModel
                    )
                }
            }
        }
    }
}