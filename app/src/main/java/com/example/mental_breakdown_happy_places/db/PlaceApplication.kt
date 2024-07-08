package com.example.mental_breakdown_happy_places.db

import android.app.Application
import android.content.Context
import androidx.room.Room

import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration

class PlaceApplication : Application() {

    lateinit var repository: PlaceRepository
        private set


    override fun onCreate() {
        super.onCreate()

        // Check and delete old database if needed (version mismatch) ---> NEEDS TO BE DELETED BEFORE NEXT RELEASE
        checkAndDeleteOldDatabase(this, "places_database", 2)

        // Configuration for MapView needed to access the map tile Server
        Configuration.getInstance().userAgentValue =
            "MentalBreakdownHappyPlaces/0.8 (Android; floy.tv.com@gmail.com)"

        // Initialize the database and repository
        val placeDao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "places_database"
        ).build().placeDao()

        repository = PlaceRepository(placeDao)
    }

    // Function to check and delete old database if needed
    fun checkAndDeleteOldDatabase(context: Context, dbName: String, currentVersion: Int) {
        val dbFile = context.getDatabasePath(dbName)
        if (dbFile.exists()) {
            kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
                val oldDb = Room.databaseBuilder(context, AppDatabase::class.java, dbName) // Use your database class here
                    .fallbackToDestructiveMigration()
                    .build()
                try{
                    val version = oldDb.openHelper.readableDatabase.version
                    if (version != currentVersion) {
                        oldDb.close()
                        withContext(Dispatchers.Main) {
                            context.deleteDatabase(dbName)
                            println("Old database deleted due to version mismatch.")
                        }
                    }
                } finally {
                    oldDb.close()
                }
            }
        }
    }
}