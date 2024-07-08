package com.example.mental_breakdown_happy_places.db

import android.app.Application
import androidx.room.Room
import org.osmdroid.config.Configuration

class PlaceApplication : Application() {

    lateinit var repository: PlaceRepository
    private set

    override fun onCreate() {
        super.onCreate()

        Configuration.getInstance().userAgentValue = "MentalBreakdownHappyPlaces/0.8 (Android; floy.tv.com@gmail.com)"

        val placeDao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "places_database"
        ).build().placeDao()

        repository = PlaceRepository(placeDao)
    }


}