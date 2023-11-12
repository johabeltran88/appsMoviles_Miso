package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Comment
import com.google.gson.Gson
import org.json.JSONObject

class CommentWebService {

    companion object {
        const val RESOURCE = "/albums/:album_id/comments"
    }

    fun create(
        albumId: Int?,
        comment: Comment,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(
            RESOURCE.replace(":album_id", albumId.toString()),
            toJSONObject(comment),
            responseListener,
            errorListener
        )
    }

    private fun toJSONObject(comment: Comment): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(comment)
        return JSONObject(jsonString)
    }

}