package com.example.test.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val id: Int?,
    val name:String?,
    val image:String?,
    val description:String?,
    val birthDate:String?,
)
