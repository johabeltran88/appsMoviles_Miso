package com.example.test.repository

import android.app.Application
import com.example.test.model.Comment
import com.example.test.network.NetworkAdapterService

class CommentRepository(private val application: Application) {

    suspend fun create(albumId: Int?, comment: Comment) {
        NetworkAdapterService.getInstance(application).createComment(albumId, comment)
    }

}