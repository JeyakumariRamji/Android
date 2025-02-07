package com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Rocket
import com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel.CosmicViewModel

@Composable
fun HomeScreen(
    viewModel: CosmicViewModel,
    onNavigateToMarsRover: () -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val apod by viewModel.apod.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Astronomy Picture of the Day",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = apod?.title.orEmpty(),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        apod?.url?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "APOD Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = apod?.explanation.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )

        // Spacer to push icons to the bottom
        Spacer(modifier = Modifier.weight(1f))

        // Icons placed at the bottom of the screen with spacing
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onNavigateToMarsRover) {
                Icon(
                    imageVector = Icons.Filled.Rocket,
                    contentDescription = "Mars Rover"
                )
            }

            IconButton(onClick = onNavigateToFavorites) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorites"
                )
            }
        }
    }
}