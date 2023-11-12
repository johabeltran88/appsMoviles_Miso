package com.example.test.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.test.model.Comment
import com.example.test.network.NetworkAdapterService
import org.json.JSONObject

class CommentRepository(private val application: Application) {

    fun create(
        albumId: Int?,
        comment: Comment,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        NetworkAdapterService.getInstance(application)
            .createComment(albumId, comment, onComplete, onError)
    }

}