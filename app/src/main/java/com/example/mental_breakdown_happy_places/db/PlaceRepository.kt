package com.example.mental_breakdown_happy_places.db

import androidx.lifecycle.LiveData
import com.example.mental_breakdown_happy_places.GeoPointConverter
import org.osmdroid.util.GeoPoint

class PlaceRepository (private val placeDao: PlaceDao) {


    val allPlaces: LiveData<List<Place>> = placeDao.getAllPlaces()



    suspend fun insert(id: Int, name:String,description: String,latitude: Double, longitude: Double, geoPoint: GeoPoint) {
        val geoPointString = GeoPointConverter().fromGeoPoint(geoPoint)
        val place = Place( id = id,name = name, description = description, latitude = latitude, longitude = longitude, geoPoint = geoPointString)
        placeDao.insert(place)
        println("New place inserted: $place.name")
    }

    fun getPlacesLiveData(): LiveData<List<Place>> {
        return placeDao.getAllPlaces()
    }
    fun getPlaceDataById(id: Int): Place {
        return placeDao.getPlaceById(id)
    }
}
