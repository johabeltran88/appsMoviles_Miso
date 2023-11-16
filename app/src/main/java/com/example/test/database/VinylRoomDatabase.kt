package com.example.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test.database.dao.AlbumsDao
import com.example.test.database.dao.ArtistsDao
import com.example.test.database.dao.CollectorsDao
import com.example.test.database.dao.CommentsDao
import com.example.test.model.Album
import com.example.test.model.Collector
import com.example.test.model.Comment
import com.example.test.model.Artist

@Database(entities = [Album::class, Collector::class, Comment::class, Artist::class], version = 1, exportSchema = false)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun collectorsDao(): CollectorsDao
    abstract fun commentsDao(): CommentsDao
    abstract fun artistsDao(): ArtistsDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}