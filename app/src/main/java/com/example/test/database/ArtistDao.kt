package com.example.test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test.model.Album
import com.example.test.model.Artist

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artists")
    fun findAll(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(artists: List<Artist>)

    @Query("DELETE FROM artists")
    fun deleteAll()

    @Query("SELECT * FROM artists WHERE id = :artistId")
    fun findById(artistId: Int?): Artist
}