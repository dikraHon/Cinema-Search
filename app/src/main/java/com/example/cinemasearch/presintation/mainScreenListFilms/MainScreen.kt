package com.example.cinemasearch.presintation.mainScreenListFilms

import android.R.attr.name
import android.R.attr.type
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinemasearch.domain.Films
import com.example.cinemasearch.presintation.collectionScreenPackage.AddToCollectionDialog
import com.example.cinemasearch.presintation.collectionScreenPackage.CollectionScreen
import com.example.cinemasearch.presintation.detailsScreenPackage.DetailsScreen
import com.example.cinemasearch.presintation.favoriteScreenPackage.FavoritesScreen
import com.example.cinemasearch.presintation.menuFilmsPackage.DrawerContent
import com.example.cinemasearch.presintation.searchPackage.CategoryChips
import com.example.cinemasearch.presintation.searchPackage.SearchTopBar
import com.example.cinemasearch.presintation.viewModelPackage.detailsViewModelPack.DetailsViewModel
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presintation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: SearchFilmsViewModel,
    favoritesViewModel: FavoritesViewModel,
    detailsViewModel: DetailsViewModel,
    collectionsViewModel: CollectionsViewModel
) {
    // 1. Navigation and UI state
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 2.Search Status
    var searchQuery by remember { mutableStateOf("") }
    val debouncedSearchQuery = remember { mutableStateOf("") }

    // 3.
    //Loading Data
    val state = viewModel.state.collectAsStateWithLifecycle().value

    var showCreateCollectionDialog by remember { mutableStateOf(false) }
    val collections by collectionsViewModel.collections.collectAsState()
    var showCollectionDialog by remember { mutableStateOf<Long?>(null) }
    if (showCollectionDialog != null) {
        AddToCollectionDialog(
            collections = collections,
            onDismiss = { showCollectionDialog = null },
            onAddToExisting = { collectionName ->
                collectionsViewModel.addToCollection(showCollectionDialog!!, collectionName)
            },
            onCreateNew = { collectionName ->
                collectionsViewModel.createCollection(collectionName)
                collectionsViewModel.addToCollection(showCollectionDialog!!, collectionName)
            }
        )
    }
    // 4. Effects
    // Search debounce (1 sec delay)
    LaunchedEffect(searchQuery) {
        delay(1000)
        debouncedSearchQuery.value = searchQuery
    }

    //Processing a search query
    LaunchedEffect(debouncedSearchQuery.value) {
        if (debouncedSearchQuery.value.length > 2) {
            viewModel.searchFilms(debouncedSearchQuery.value)
        } else if (debouncedSearchQuery.value.isEmpty()) {
            viewModel.loadFilms()
        }
    }
    //Showing errors
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

    // 5. Loading movies on startup
    LaunchedEffect(Unit) {
        viewModel.loadFilms()
    }

    // 6. UI
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
                },
                collections = collections,
                onCreateCollection = { showCreateCollectionDialog = true }
            )
        }
    ) {
        Scaffold(
            topBar = {
                Column {
                    SearchTopBar(
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onFavoritesClick = { navController.navigate("favorites") }
                    )

                    // Показываем чипы только на главном экране
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
                                    navController.navigate("details/$filmId")},
                                onCollectionClick = { film ->
                                    showCollectionDialog = film.id // Теперь принимает Films, но использует только id
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {
                            Text("No movie", modifier = Modifier.fillMaxSize())
                        }
                    }
                }
                composable("favorites") {
                    FavoritesScreen(
                        favoritesViewModel = favoritesViewModel,
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
                        viewModel = detailsViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable(
                    route = "collection/{name}",
                    arguments = listOf(navArgument("name") { type = NavType.StringType })
                ) { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name") ?: ""
                    CollectionScreen(
                        collectionName = name,
                        viewModel = collectionsViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onFilmClick = { filmId ->
                            navController.navigate("details/$filmId")
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable("settings") {
                    Text("Settings Screen")
                }
            }
        }
    }
    if (showCreateCollectionDialog) {
        AlertDialog(
            onDismissRequest = { showCreateCollectionDialog = false },
            title = { Text("New Collection") },
            text = {
                var name by remember { mutableStateOf("") }
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Collection name") }
                )
            },
            confirmButton = {
                var name by remember { mutableStateOf("") }
                Button(
                    onClick = {
                        collectionsViewModel.createCollection(name)
                        showCreateCollectionDialog = false
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateCollectionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}



