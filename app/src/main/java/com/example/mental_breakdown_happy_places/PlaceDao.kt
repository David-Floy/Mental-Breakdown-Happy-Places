package com.example.mental_breakdown_happy_places

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Place) : Long

    @Query("SELECT * FROM places")
    fun getAllPlaces(): List<Place>

    @Delete
    fun delete(place: Place)
    @Query("SELECT * FROM places WHERE id = :id")
    fun getTextById(id: Int): Flow<Place?>
}