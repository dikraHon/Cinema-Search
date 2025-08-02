package com.example.cinemasearch.presintation.menuFilmsPackage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit,
    collections: List<String>,
    onCreateCollection: () -> Unit
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Collections",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                IconButton(
                    onClick = onCreateCollection,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add collection")
                }
            }

            if (collections.isEmpty()) {
                Text(
                    text = "No collections yet",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            } else {
                collections.forEach { collection ->
                    DrawerItem(
                        text = collection,
                        route = "collection/$collection",
                        onItemSelected = onItemSelected
                    )
                }
            }

            DrawerItem(
                text = "Settings",
                route = "settings",
                onItemSelected = onItemSelected
            )
        }
    }
}