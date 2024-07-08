package com.example.mental_breakdown_happy_places.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {





    suspend fun insertPlace(id: Int, name:String,description: String,latitude: Double, longitude: Double, geoPoint: GeoPoint) {
        repository.insert(id,name,description,latitude,longitude, geoPoint)
    }




    fun getAllPlaces() :LiveData<List<Place>> = repository.getPlacesLiveData()

    fun getTextById(id: Int) : Place = repository.getPlaceDataById(id)
}

