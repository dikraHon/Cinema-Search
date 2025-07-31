package com.example.cinemasearch.presintation.screenListFilms

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinemasearch.presintation.menuFilmsPackage.DrawerContent
import com.example.cinemasearch.presintation.viewModelPackage.SearchFilmsViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: SearchFilmsViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state = viewModel.filmsState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadFilms()
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
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
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
                                onMenuClick = { scope.launch { drawerState.open() } }
                            )
                        }
                        else -> {
                            // Показать пустое состояние или сообщение об ошибке
                        }
                    }
                }
                composable("favorites") {
                    // Здесь будет экран избранного
                    Text("Favorites Screen")
                }
                composable("settings") {
                    // Здесь будет экран настроек
                    Text("Settings Screen")
                }
            }
        }
    }
}