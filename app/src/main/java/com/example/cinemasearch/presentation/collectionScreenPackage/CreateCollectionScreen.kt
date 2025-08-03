package com.example.cinemasearch.presentation.collectionScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.presentation.rememberStrings
import com.example.cinemasearch.presentation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel

@Composable
fun CreateCollectionScreen(
    viewModel: CollectionsViewModel,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    val string = rememberStrings()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(string.collectName) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.createCollection(name)
                onBack()
            },
            modifier = Modifier.padding(top = 16.dp),
            enabled = name.isNotBlank()
        ) {
            Text(string.create)
        }
    }
}