package com.example.cinemasearch.presentation.menuFilmsPackage

import android.R.attr.endY
import android.R.attr.shape
import android.R.attr.startY
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cinemasearch.R
import com.example.cinemasearch.presentation.componentsPack.rememberStrings

@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit,
) {
    val string = rememberStrings()
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.my_bag),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.9f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        1f to MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ),
                )
                .padding(top = 48.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    alignment = Alignment.CenterStart
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = string.cinemaSearch,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFD32F2F), // Красный 700
                )
            }
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                DrawerItem(
                    text = string.films,
                    route = "films",
                    onItemSelected = onItemSelected,
                )
                DrawerItem(
                    text = string.favorites,
                    route = "favorites",
                    onItemSelected = onItemSelected
                )
                DrawerItem(
                    text = string.collections,
                    route = "collections",
                    onItemSelected = onItemSelected
                )
                DrawerItem(
                    text = string.settings,
                    route = "settings",
                    onItemSelected = onItemSelected
                )
            }
        }
    }
}