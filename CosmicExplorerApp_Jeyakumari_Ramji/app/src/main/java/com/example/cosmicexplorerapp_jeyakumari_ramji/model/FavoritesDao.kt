package com.example.cosmicexplorerapp_jeyakumari_ramji.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM MarsPhoto")
    suspend fun getAll(): List<MarsPhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(photo: MarsPhoto): Long

    @Delete
    suspend fun delete(photo: MarsPhoto)
}
