package com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhoto
import com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel.CosmicViewModel

@Composable
fun DetailScreen(photoId: String?, viewModel: CosmicViewModel) {
    val photo by produceState<MarsPhoto?>(null, photoId) {
        value = viewModel.getPhotoById(photoId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        photo?.let {
            AsyncImage(
                model = it.imgSrc,
                contentDescription = "Mars Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Rover: ${it.roverName ?: "Unknown"}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Camera: ${it.cameraName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Earth Date: ${it.earthDate}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Sol (Martian Day): ${it.sol}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))

            // Normalize camera name
            val description = when (it.cameraName.trim().uppercase()) {
                "FHAZ" -> "Front Hazard Avoidance Camera capturing obstacles."
                "RHAZ" -> "Rear Hazard Avoidance Camera ensuring safe movement."
                "MAST" -> "Mast Camera capturing high-resolution images."
                "NAVCAM" -> "Navigation Camera aiding rover movement."
                "CHEMCAM" -> "ChemCam performing laser spectroscopy."
                else -> "An image taken by NASA's Mars Rover."
            }

            Text(
                text = description,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 6.dp)
            )
        } ?: Text("Photo not found", style = MaterialTheme.typography.titleLarge)
    }
}