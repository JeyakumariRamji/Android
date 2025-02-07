package com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.Apod
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhoto
import com.example.cosmicexplorerapp_jeyakumari_ramji.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.cosmicexplorerapp_jeyakumari_ramji.repository.CosmicRepository
import com.example.cosmicexplorerapp_jeyakumari_ramji.database.AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CosmicViewModel(
    application: Application,  // Use Application context
    private val apiService: ApiService
) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val repository = CosmicRepository(apiService, database)

    private val _apod = MutableStateFlow<Apod?>(null)
    val apod: StateFlow<Apod?> = _apod

    private val _marsPhotos = MutableStateFlow<List<MarsPhoto>>(emptyList())
    val marsPhotos: StateFlow<List<MarsPhoto>> = _marsPhotos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _favorites = MutableStateFlow<List<MarsPhoto>>(emptyList())
    val favorites: StateFlow<List<MarsPhoto>> = _favorites

    init {
        fetchApod()
        fetchMarsPhotos()  // Updated here, no argument needed
        fetchFavorites()
    }

    private fun fetchApod() {
        viewModelScope.launch {
            try {
                _apod.value = repository.getApod("Your_NASA_API_KEY")
            } catch (e: Exception) {
                Log.e("CosmicViewModel", "Error fetching APOD: ${e.message}")
            }
        }
    }

    fun fetchMarsPhotos() {
        viewModelScope.launch {
            try {
                val allPhotos = repository.getMarsPhotos("Your_NASA_API_KEY", 1000)
                _marsPhotos.value = allPhotos
            } catch (e: Exception) {
                Log.e("CosmicViewModel", "Error fetching Mars photos: ${e.message}")
                _error.value = "Failed to fetch Mars photos: ${e.message}"
            }
        }
    }

    fun getPhotoById(photoId: String?): MarsPhoto? {
        val id = photoId?.toIntOrNull() ?: return null  // Convert to Int safely
        return _marsPhotos.value.find { it.id == id }
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            try {
                _favorites.value = repository.getFavorites()
            } catch (e: Exception) {
                _error.value = "Failed to fetch favorites: ${e.message}"
                Log.e("CosmicViewModel", "Error fetching Favorites: ${e.message}")
            }
        }
    }

    fun addToFavorites(photo: MarsPhoto) {
        // Find the photo and mark it as favorite
        val updatedPhotos = _marsPhotos.value.map {
            if (it.id == photo.id) it.copy(isFavorite = true) else it
        }
        _marsPhotos.value = updatedPhotos
        // Add to favorites list if not already present
        if (!_favorites.value.any { it.id == photo.id }) {
            _favorites.value = _favorites.value + photo.copy(isFavorite = true)
        }
    }

    fun removeFromFavorites(photo: MarsPhoto) {
        // Find the photo and remove it from favorites
        val updatedPhotos = _marsPhotos.value.map {
            if (it.id == photo.id) it.copy(isFavorite = false) else it
        }
        _marsPhotos.value = updatedPhotos
        // Remove from favorites list
        _favorites.value = _favorites.value.filter { it.id != photo.id }
    }
}