package com.example.mental_breakdown_happy_places.db

import kotlinx.coroutines.flow.Flow

class PlaceRepository (private val placeDao: PlaceDao) {






    suspend fun insert(id: Int, name:String,description: String,latitude: Double, longitude: Double) {
        val place = Place( id = id,name = name, description = description, latitude = latitude, longitude = longitude)
        placeDao.insert(place)
        println("New place inserted: $place.name")
    }

    fun getAllPlaces(): List<Place> {
        return placeDao.getAllPlaces()
    }
    fun getTextById(id: Int): Flow<Place?> {
        return placeDao.getTextById(id)
    }
}
