package com.example.mental_breakdown_happy_places.db

import androidx.lifecycle.LiveData
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
    fun getAllPlaces(): LiveData<List<Place>>

    @Delete
    fun delete(place: Place)


    @Query("SELECT name FROM places WHERE id = :id")
    fun getTextById(id: Int): String

    @Query("SELECT COUNT(*) FROM places")
    fun getCount() : Int
}