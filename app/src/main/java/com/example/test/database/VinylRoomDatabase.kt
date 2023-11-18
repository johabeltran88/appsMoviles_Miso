package com.example.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test.model.Album
import com.example.test.model.Artist

@Database(
    entities = [Album::class, Artist::class],
    version = 1,
    exportSchema = false
)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao

    companion object {
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}