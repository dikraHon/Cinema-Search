package com.example.cinemasearch.presentation.collectionScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.presentation.rememberStrings
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@Composable
fun CollectionsScreen(
    viewModel: CollectionsViewModel,
    onCreateCollection: () -> Unit,
    onCollectionSelected: (Long) -> Unit
) {
    val collections by viewModel.collections.collectAsState()
    val string = rememberStrings()
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onCreateCollection,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = string.createNewCollection,
                color = MaterialTheme.colorScheme.onSurface
            )
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