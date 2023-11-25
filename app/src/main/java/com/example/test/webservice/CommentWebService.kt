package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.test.common.getRequest
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
            toJSONObject(comment, albumId),
            responseListener,
            errorListener
        )
    }

    fun getAll(
        albumId: Int?, responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return getRequest(
            RESOURCE.replace(":album_id", albumId.toString()),
            responseListener,
            errorListener
        )
    }

    private fun toJSONObject(comment: Comment, albumId: Int?): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(comment).replace("\"albumId\":$albumId,", "")
        return JSONObject(jsonString)
    }

}