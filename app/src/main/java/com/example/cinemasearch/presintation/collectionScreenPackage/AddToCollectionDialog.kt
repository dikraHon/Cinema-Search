package com.example.cinemasearch.presintation.collectionScreenPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddToCollectionDialog(
    collections: List<String>,
    onDismiss: () -> Unit,
    onAddToExisting: (String) -> Unit,
    onCreateNew: (String) -> Unit
) {
    var selectedCollection by remember { mutableStateOf("") }
    var newCollectionName by remember { mutableStateOf("") }
    var showNewCollectionField by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add to Collection") },
        text = {
            Column {
                if (!showNewCollectionField && collections.isNotEmpty()) {
                    Text("Select collection:", modifier = Modifier.padding(bottom = 8.dp))
                    LazyColumn {
                        items(collections) { collection ->
                            RadioButton(
                                selected = selectedCollection == collection,
                                onClick = { selectedCollection = collection }
                            )
                            Text(collection)
                        }
                    }
                }

                if (showNewCollectionField || collections.isEmpty()) {
                    TextField(
                        value = newCollectionName,
                        onValueChange = { newCollectionName = it },
                        label = { Text("New collection name") }
                    )
                }

                TextButton(
                    onClick = { showNewCollectionField = !showNewCollectionField },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (showNewCollectionField) "Select existing" else "Create new")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (showNewCollectionField || collections.isEmpty()) {
                        onCreateNew(newCollectionName)
                    } else {
                        onAddToExisting(selectedCollection)
                    }
                    onDismiss()
                },
                enabled = if (showNewCollectionField || collections.isEmpty()) {
                    newCollectionName.isNotBlank()
                } else {
                    selectedCollection.isNotBlank()
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}