package com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhoto
import com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel.CosmicViewModel
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

//The composable displays a list of favorite Mars photos stored in the app
@Composable
fun FavoritesScreen(viewModel: CosmicViewModel) {
    val favorites by viewModel.favorites.collectAsState()

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorites added yet!",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favorites) { photo ->
                FavoritePhotoCard(photo) { selectedPhoto ->
                    viewModel.removeFromFavorites(selectedPhoto)
                }
            }
        }
    }
}
//The composable represents a single favorite photo in a card layout.
@Composable
fun FavoritePhotoCard(photo: MarsPhoto, onRemoveClick: (MarsPhoto) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = photo.imgSrc.takeIf { !it.isNullOrEmpty() } ?: "https://via.placeholder.com/200",
                contentDescription = "Favorite Mars Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Earth Date: ${photo.earthDate}")

            // Remove Button (correct placement)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onRemoveClick(photo) }) {
                    Icon(Icons.Default.Favorite, contentDescription = "Remove from Favorites", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}