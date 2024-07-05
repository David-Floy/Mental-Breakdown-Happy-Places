package com.example.mental_breakdown_happy_places

import android.content.Context
import androidx.room.Room

class PlaceRepository private constructor(context: Context) {
    private val placeDao: PlaceDao

    init {
        val database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "places_db"
        )
            .allowMainThreadQueries() // Verwende nicht auf dem Hauptthread in Produktion
            .build()
        placeDao = database.placeDao()
    }

    companion object {
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(context: Context): PlaceRepository {
            return instance ?: synchronized(this) {
                instance ?: PlaceRepository(context).also { instance = it }
            }
        }
    }

    fun insert(place: Place) {
        placeDao.insert(place)
    }

    fun getAllPlaces(): List<Place> {
        return placeDao.getAllPlaces()
    }
}
