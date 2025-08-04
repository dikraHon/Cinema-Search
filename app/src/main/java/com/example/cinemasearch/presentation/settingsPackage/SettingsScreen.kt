package com.example.cinemasearch.presentation.settingsPackage

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.R

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    themeViewModel: ThemeViewModel
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Language Section
        Text(
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(onClick = {
            LocalizationManager.setAppLocale(context, "en")
            activity?.recreate()
        }) { Text("English") }

        // Кнопка русского языка
        Button(onClick = {
            LocalizationManager.setAppLocale(context, "ru")
            activity?.recreate()
        }) { Text("Русский") }

        // Переключатель темы
        Switch(
            checked = themeViewModel.isDarkTheme,
            onCheckedChange = {
                themeViewModel.toggleTheme()
                activity?.recreate()
            }
        )
        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.back))
        }
    }
}
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}