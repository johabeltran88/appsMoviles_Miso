package com.example.test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test.model.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    fun findAll(): List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM albums")
    fun deleteAll()

    @Query("SELECT * FROM albums WHERE id = :albumId")
    fun findById(albumId: Int?): Album
}