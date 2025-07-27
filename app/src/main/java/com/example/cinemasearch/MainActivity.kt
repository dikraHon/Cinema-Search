package com.example.cinemasearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.cinemasearch.di.MyApp
import com.example.cinemasearch.presintation.FactoryViewModel
import com.example.cinemasearch.presintation.SearchFilmsViewModel
import com.example.cinemasearch.presintation.adapterFilms.FilmsList
import com.example.cinemasearch.ui.theme.CinemaSearchTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: FactoryViewModel
    private val viewModel: SearchFilmsViewModel by viewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CinemaSearchTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }


    @Composable
    fun MainScreen(viewModel: SearchFilmsViewModel) {
        val state = viewModel.filmsState.collectAsStateWithLifecycle().value
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        // Загрузка данных при первом отображении
        LaunchedEffect(Unit) {
            viewModel.loadFilms()
        }

        // Обработка ошибок
        LaunchedEffect(state.error) {
            state.error?.let { error ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = error ?: "Unknown error",
                        actionLabel = "Retry"
                    )
                    viewModel.clearError()
                }
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
                state.films.isNotEmpty() -> {
                    FilmsList(
                        modifier = Modifier.padding(innerPadding),
                        films = state.films,
                        onRetry = { viewModel.loadFilms() }
                    )
                }
                else -> {
                    // Показать пустое состояние или сообщение об ошибке
                }
            }
        }
    }
}


