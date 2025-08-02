package com.example.cinemasearch.presintation.collectionScreenPackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.domain.CollectionFilms
import com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

// CollectionsScreen.kt
@Composable
fun CollectionsScreen(
    viewModel: CollectionsViewModel,
    onCreateCollection: () -> Unit,
    onCollectionSelected: (Long) -> Unit
) {
    val collections by viewModel.collections.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onCreateCollection,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Create New Collection")
        }

        LazyColumn {
            items(collections) { collection ->
                CollectionItem(
                    collection = collection,
                    onClick = { onCollectionSelected(collection.id) }
                )
            }
        }
    }
}

@Composable
fun CollectionItem(
    collection: CollectionFilms,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(collection.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}