package com.example.mental_breakdown_happy_places.db

import android.app.Application
import androidx.room.Room

class PlaceApplication : Application() {

    lateinit var repository: PlaceRepository
    private set

    override fun onCreate() {
        super.onCreate()

        val placeDao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "places_database"
        ).build().placeDao()

        repository = PlaceRepository(placeDao)
    }


}