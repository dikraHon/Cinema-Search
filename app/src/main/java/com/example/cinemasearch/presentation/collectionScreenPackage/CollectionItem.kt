package com.example.cinemasearch.presentation.collectionScreenPackage

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.cinemasearch.domain.modelData.CollectionFilms
import com.example.cinemasearch.presentation.componentsPack.rememberStrings

@Suppress("DEPRECATION")
@Composable
fun CollectionItem(
    collection: CollectionFilms,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val string = rememberStrings()

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
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "${string.movieSelection}: ${collection.name}")
                }
                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(intent, string.shareSelection),
                    null
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = string.shareSelection,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}