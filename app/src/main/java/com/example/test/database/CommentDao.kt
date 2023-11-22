package com.example.test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test.model.Comment

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments WHERE albumId = :albumId")
    fun findAllByAlbumId(albumId: Int?): List<Comment>

    @Query("DELETE FROM comments WHERE albumId = :albumId")
    fun deleteAllByAlbumId(albumId: Int?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(comments: List<Comment>)

}