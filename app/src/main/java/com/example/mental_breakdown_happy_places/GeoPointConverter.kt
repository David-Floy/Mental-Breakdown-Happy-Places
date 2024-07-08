package com.example.mental_breakdown_happy_places

import androidx.room.TypeConverter
import org.osmdroid.util.GeoPoint

class GeoPointConverter {
    @TypeConverter
    fun fromGeoPoint(geoPoint: GeoPoint?): String? {
        return geoPoint?.let { "${it.latitude},${it.longitude}" }
    }

    @TypeConverter
    fun toGeoPoint(value: String?): GeoPoint? {
        return value?.let {
            val (lat, lng) = it.split(",")
            GeoPoint(lat.toDouble(), lng.toDouble())
        }
    }
}