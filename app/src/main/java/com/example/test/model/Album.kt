package com.example.test.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey val id: Int?,
    val name:String?,
    val cover:String?,
    val releaseDate:String?,
    val description:String?,
    val genre:String?,
    val recordLabel:String?,
)
