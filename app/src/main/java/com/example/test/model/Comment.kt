package com.example.test.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey val id: Int?,
    val description: String?,
    val rating: Int?,
    val albumId: Int?,
    @Ignore val collector: Collector?
) {
    constructor(id: Int?, description: String?, rating: Int?, albumId: Int?)
            : this(id, description, rating, albumId, null)
}
