package com.example.cinemasearch.presentation.collectionScreenPackage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.R
import com.example.cinemasearch.presentation.componentsPack.rememberStrings
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@Composable
fun CollectionsScreen(
    viewModel: CollectionsViewModel,
    onCreateCollection: () -> Unit,
    onCollectionSelected: (Long) -> Unit
) {
    val collections by viewModel.collections.collectAsState()
    val string = rememberStrings()
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.my_bag),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.9f
        )
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
}