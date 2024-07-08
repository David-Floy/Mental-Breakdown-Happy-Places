package com.example.mental_breakdown_happy_places.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mental_breakdown_happy_places.GeoPointConverter

@Database(entities = [Place::class], version = 2)
@TypeConverters(GeoPointConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao


    // Migration definition (usually in the same file)


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
