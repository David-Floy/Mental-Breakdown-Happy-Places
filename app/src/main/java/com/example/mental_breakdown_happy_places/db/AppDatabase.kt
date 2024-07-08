package com.example.mental_breakdown_happy_places.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mental_breakdown_happy_places.GeoPointConverter

@Database(entities = [Place::class], version = 1)
@TypeConverters(GeoPointConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "places_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
