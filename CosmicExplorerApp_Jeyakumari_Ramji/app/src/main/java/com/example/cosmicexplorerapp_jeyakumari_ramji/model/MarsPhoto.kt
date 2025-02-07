package com.example.cosmicexplorerapp_jeyakumari_ramji.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MarsPhoto")
data class MarsPhoto(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val sol: Int,
    val imgSrc: String,
    val earthDate: String,
    val cameraName: String,
    val roverName: String,
    val rover: MarsRover?,
    var isFavorite: Boolean = false
)

data class MarsRover(
    val name: String,
    val launchDate: String,
    val landingDate: String,
    val status: String
)
