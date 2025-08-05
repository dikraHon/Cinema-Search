package com.example.cinemasearch.presentation.searchPackage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.R
import com.example.cinemasearch.presentation.viewModelPackage.mainScreenViewModel.FilmCategory

@Composable
fun CategoryChips(
    currentCategory: FilmCategory,
    onCategorySelected: (FilmCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .background(color = Color(0xFF181212))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        FilmCategory.entries.forEach { category ->
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        if (currentCategory == category)
                            Color(0x99D32F2F)
                        else
                            Color(0x4DFFFFFF),
                        MaterialTheme.shapes.medium
                    )
                    .border(
                        width = if (currentCategory == category) 1.dp else 0.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = category.displayName,
                    color = if (currentCategory == category)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}