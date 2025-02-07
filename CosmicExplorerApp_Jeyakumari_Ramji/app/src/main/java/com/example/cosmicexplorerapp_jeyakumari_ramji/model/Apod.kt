package com.example.cosmicexplorerapp_jeyakumari_ramji.model

// Data class for API response
data class Apod(
    val date: String,
    val explanation: String,
    val title: String,
    val url: String
)