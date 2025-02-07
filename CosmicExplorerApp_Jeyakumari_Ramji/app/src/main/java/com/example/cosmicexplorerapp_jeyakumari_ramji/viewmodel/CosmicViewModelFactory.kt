package com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cosmicexplorerapp_jeyakumari_ramji.repository.CosmicRepository
import android.app.Application
import com.example.cosmicexplorerapp_jeyakumari_ramji.network.ApiService

class CosmicViewModelFactory(
    private val application: Application,
    private val apiService: ApiService,
    private val repository: CosmicRepository // Assuming repository constructor requires apiService and context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CosmicViewModel::class.java)) {
            return CosmicViewModel(application, apiService) as T // Pass the application and apiService
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}