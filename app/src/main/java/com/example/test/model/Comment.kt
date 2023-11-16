package com.example.test.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments_table")
data class Comment(
    @PrimaryKey val id: Int?,
    val description: String?,
    val rating: Int?,
    val collector: Collector?,
)
