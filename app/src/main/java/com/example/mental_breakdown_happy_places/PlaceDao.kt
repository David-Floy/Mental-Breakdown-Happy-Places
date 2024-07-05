package com.example.mental_breakdown_happy_places

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {
    @Insert
    fun insert(place: Place)

    @Query("SELECT * FROM places")
    fun getAllPlaces(): List<Place>

    @Delete
    fun delete(place: Place)
}