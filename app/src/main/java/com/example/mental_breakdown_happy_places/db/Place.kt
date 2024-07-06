package com.example.mental_breakdown_happy_places.db
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var description: String,
    var latitude: Double,
    var longitude: Double
)
