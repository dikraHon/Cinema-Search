package com.example.cinemasearch.presentation.menuFilmsPackage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.presentation.rememberStrings

@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit,
) {
    val string = rememberStrings()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = string.cinemaSearch,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            DrawerItem(
                text = string.films,
                route = "films",
                onItemSelected = onItemSelected
            )
            DrawerItem(
                text = string.favorites,
                route = "favorites",
                onItemSelected = onItemSelected
            )
            DrawerItem(
                text = string.collections,
                route = "collections",
                onItemSelected = onItemSelected
            )
            DrawerItem(
                text = string.settings,
                route = "settings",
                onItemSelected = onItemSelected
            )
        }
    }
}