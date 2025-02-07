package com.example.cosmicexplorerapp_jeyakumari_ramji.network

import com.example.cosmicexplorerapp_jeyakumari_ramji.model.Apod
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhotosResponse

interface ApiService {
    @GET("planetary/apod")
    suspend fun getApod(@Query("api_key") apiKey: String): Apod

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotos(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String
    ): MarsPhotosResponse
}
