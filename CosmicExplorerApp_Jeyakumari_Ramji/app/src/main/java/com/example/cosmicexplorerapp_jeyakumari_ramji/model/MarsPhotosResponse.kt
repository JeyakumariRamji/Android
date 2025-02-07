package com.example.cosmicexplorerapp_jeyakumari_ramji.model

import com.google.gson.annotations.SerializedName

data class MarsPhotosResponse(
    @SerializedName("photos") val photos: List<MarsPhotoResponse>
)

data class MarsPhotoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("sol") val sol: Int,
    @SerializedName("img_src") val imgSrc: String?,
    @SerializedName("earth_date") val earthDate: String?,
    @SerializedName("camera") val camera: Camera,
    @SerializedName("rover") val rover: Rover
)

data class Camera(
    @SerializedName("name") val name: String
)

data class Rover(
    @SerializedName("name") val name: String
)