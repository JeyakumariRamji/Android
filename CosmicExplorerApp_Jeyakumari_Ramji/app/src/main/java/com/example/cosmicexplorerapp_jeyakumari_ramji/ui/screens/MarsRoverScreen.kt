package com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhoto
import com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel.CosmicViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.TextFieldDefaults
import kotlinx.coroutines.launch

@Composable
fun MarsRoverScreen(
    navController: NavController,
    viewModel: CosmicViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredPhotos by remember { mutableStateOf<List<MarsPhoto>>(emptyList()) }

    val photos: List<MarsPhoto> = viewModel.marsPhotos.collectAsState().value

    val pageSize = 5  // Load 2 images at a time
    var itemsToShow by remember { mutableIntStateOf(pageSize) } // Start with 5 items

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Filter photos based on search query
    LaunchedEffect(searchQuery) {
        filteredPhotos = photos.filter {
            it.cameraName?.contains(searchQuery, ignoreCase = true) == true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        SearchBar(searchQuery = searchQuery, onSearchQueryChanged = { searchQuery = it })
        Spacer(modifier = Modifier.height(16.dp))

        // Camera Names List
        CameraNamesList(photos = filteredPhotos)
        Spacer(modifier = Modifier.height(16.dp))
        

        // Ensure LazyColumn is scrollable
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                items(filteredPhotos.take(itemsToShow).size) { index ->
                    val photo = filteredPhotos[index]
                    PhotoItem(photo = photo, onClick = {
                        navController.navigate("detail/${photo.id}")
                    }, viewModel = viewModel)
                }

                // Load More Button at the bottom
                item {
                    if (itemsToShow < filteredPhotos.size) {
                        Button(
                            onClick = {
                                itemsToShow += pageSize
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(itemsToShow - 1)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text("Load More")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: String, onSearchQueryChanged: (String) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = { Text("Search Camera") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun CameraNamesList(photos: List<MarsPhoto>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        photos.distinctBy { it.cameraName }.forEach { photo ->
            Text(
                text = photo.cameraName ?: "Unknown Camera",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        // Handle camera name selection, update images list accordingly
                        Log.d("Camera", "Selected camera: ${photo.cameraName}")
                    },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PhotoItem(photo: MarsPhoto, onClick: (MarsPhoto) -> Unit, viewModel: CosmicViewModel) {
    val favorites by viewModel.favorites.collectAsState()

    // Check if this photo is currently a favorite
    val isFavorite = favorites.any { it.id == photo.id }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(photo) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = photo.imgSrc,
            contentDescription = "Mars Rover Image",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = "Rover: ${photo.roverName ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Camera: ${photo.cameraName ?: "Unknown"}", style = MaterialTheme.typography.bodySmall)
        }

        // Favorite Button
        IconButton(
            onClick = {
                if (isFavorite) {
                    viewModel.removeFromFavorites(photo)
                } else {
                    viewModel.addToFavorites(photo)
                }
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}