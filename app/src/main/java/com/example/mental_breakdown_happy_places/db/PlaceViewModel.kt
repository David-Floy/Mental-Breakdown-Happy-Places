package com.example.mental_breakdown_happy_places.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {





    suspend fun insertPlace(id: Int, name:String,description: String,latitude: Double, longitude: Double ) {
        repository.insert(id,name,description,latitude,longitude)
    }




    fun getAllPlaces() :LiveData<List<Place>> = repository.getPlacesLiveData()

    fun getTextById(id: Int) = repository.getTextById(id)
}

