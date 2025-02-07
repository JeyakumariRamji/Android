package com.example.cosmicexplorerapp_jeyakumari_ramji.database

import androidx.room.TypeConverter
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsRover
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//MarsRover object to a JSON string for storage and back to a MarsRover object when retrieving data
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMarsRover(rover: MarsRover?): String? {
        return gson.toJson(rover)
    }

    @TypeConverter
    fun toMarsRover(roverString: String?): MarsRover? {
        return gson.fromJson(roverString, object : TypeToken<MarsRover?>() {}.type)
    }
}