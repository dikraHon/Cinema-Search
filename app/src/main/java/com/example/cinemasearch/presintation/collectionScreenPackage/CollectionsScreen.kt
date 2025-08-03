package com.example.cinemasearch.presintation.collectionScreenPackage

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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

@Suppress("DEPRECATION")
@Composable
fun CollectionItem(
    collection: CollectionFilms,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = collection.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Подборка фильмов: ${collection.name}")
                }
                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(intent, "Поделиться подборкой"),
                    null
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Поделиться подборкой",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}