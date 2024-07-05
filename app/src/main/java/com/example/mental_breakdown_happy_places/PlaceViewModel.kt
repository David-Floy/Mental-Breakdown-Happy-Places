package com.example.mental_breakdown_happy_places

import androidx.lifecycle.ViewModel

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {
    suspend fun insertPlace(id: Int, name:String,description: String,latitude: Double, longitude: Double ) {
        repository.insert(id,name,description,latitude,longitude)
    }

    fun getTextById(id: Int) = repository.getTextById(id)
}

