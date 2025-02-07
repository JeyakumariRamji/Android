package com.example.cosmicexplorerapp_jeyakumari_ramji.repository

import android.util.Log
import com.example.cosmicexplorerapp_jeyakumari_ramji.database.AppDatabase
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.Apod
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhoto
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsRover
import com.example.cosmicexplorerapp_jeyakumari_ramji.network.ApiService

class CosmicRepository(
    private val apiService: ApiService,
    private val database: AppDatabase
) {
    // Fetch Mars photos
    suspend fun getMarsPhotos(apiKey: String, sol: Int): List<MarsPhoto> {
        try {
            // Make the API call to fetch Mars photos
            val response = apiService.getMarsPhotos(sol, apiKey)

            // Log the raw API response to inspect its structure
            Log.d("ApiService", "API Response: ${response.photos}")

            // Map the API response to the MarsPhoto entity
            return response.photos.map { photoResponse ->
                MarsPhoto(
                    id = photoResponse.id,
                    sol = photoResponse.sol,
                    imgSrc = photoResponse.imgSrc ?: "", // Default empty string if null
                    earthDate = photoResponse.earthDate ?: "", // Default empty string if null
                    cameraName = photoResponse.camera.name, // Camera name comes from the response
                    roverName = photoResponse.rover.name, // Rover name comes from the response
                    rover = photoResponse.rover as? MarsRover ?: MarsRover("Unknown", "Unknown", "Unknown", "Unknown")
                )
            }
        } catch (e: Exception) {
            Log.e("ApiService", "Error fetching Mars photos: ${e.message}")
            throw e
        }
    }

    // Fetch all favorite photos
    suspend fun getFavorites(): List<MarsPhoto> = database.favoritesDao().getAll()

    suspend fun getApod(apiKey: String): Apod {
        // Call the API to get the APOD data
        return apiService.getApod(apiKey)
    }
}