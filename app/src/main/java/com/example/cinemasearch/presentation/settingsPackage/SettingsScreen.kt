package com.example.cinemasearch.presentation.settingsPackage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.R

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

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

        Button(
            onClick = {
                LocalizationManager.setAppLocale(context, "en")
                (context as? android.app.Activity)?.recreate()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.english))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                LocalizationManager.setAppLocale(context, "ru")
                (context as? android.app.Activity)?.recreate()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.russian))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.back))
        }
    }
}