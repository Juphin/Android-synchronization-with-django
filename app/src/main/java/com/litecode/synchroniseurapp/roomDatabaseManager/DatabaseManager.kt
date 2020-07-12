package com.litecode.synchroniseurapp.roomDatabaseManager


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PubTable::class], version = 1)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun getPublicationDao(): MyPubDao

    companion object {
        private var INSTANCE: DatabaseManager? = null

        fun getDatabase(context: Context): DatabaseManager {
            if(INSTANCE == null){
                synchronized(DatabaseManager::class.java){
                    if (INSTANCE == null) {
                        // Get DatabaseManager database instance
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DatabaseManager::class.java, "pub_database"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}