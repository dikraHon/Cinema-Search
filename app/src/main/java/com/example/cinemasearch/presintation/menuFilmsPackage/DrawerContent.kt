package com.example.cinemasearch.presintation.menuFilmsPackage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Cinema Search",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            DrawerItem(
                text = "Films",
                route = "films",
                onItemSelected = onItemSelected
            )
            DrawerItem(
                text = "Favorites",
                route = "favorites",
                onItemSelected = onItemSelected
            )
            DrawerItem(
                text = "Collections",
                route = "collections",
                onItemSelected = onItemSelected
            )
            DrawerItem(
                text = "Settings",
                route = "settings",
                onItemSelected = onItemSelected
            )
        }
    }
}