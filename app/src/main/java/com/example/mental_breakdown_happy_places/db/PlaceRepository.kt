package com.example.mental_breakdown_happy_places.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class PlaceRepository (private val placeDao: PlaceDao) {


    val allPlaces: LiveData<List<Place>> = placeDao.getAllPlaces()



    suspend fun insert(id: Int, name:String,description: String,latitude: Double, longitude: Double) {
        val place = Place( id = id,name = name, description = description, latitude = latitude, longitude = longitude)
        placeDao.insert(place)
        println("New place inserted: $place.name")
    }

    fun getPlacesLiveData(): LiveData<List<Place>> {
        return placeDao.getAllPlaces()
    }
    fun getTextById(id: Int): String {
        return placeDao.getTextById(id)
    }
}
