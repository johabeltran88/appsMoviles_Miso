package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.test.common.getRequest
import com.example.test.common.postRequest
import com.example.test.model.Album
import com.google.gson.Gson
import org.json.JSONObject

class AlbumWebService {

    companion object {
        const val RESOURCE = "/albums"
    }

    fun getAll(
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return getRequest(RESOURCE, responseListener, errorListener)
    }

    fun create(
        album: Album,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCE, toJSONObject(album), responseListener, errorListener)
    }

    private fun toJSONObject(album: Album): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(album)
        return JSONObject(jsonString)
    }

    fun albumWithArtist(
        albumId: Int?,
        artistId: Int?,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest{
        return postRequest("$RESOURCE/$albumId/musicians/$artistId", JSONObject(), responseListener, errorListener)
    }
}