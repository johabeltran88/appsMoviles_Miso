package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.test.common.getRequest
import com.example.test.common.postRequest
import com.example.test.model.Artist
import com.google.gson.Gson
import org.json.JSONObject

class ArtistWebService {

    companion object {
        const val RESOURCE = "/musicians"
    }

    fun getAll(
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return getRequest(RESOURCE, responseListener, errorListener)
    }

    fun create(
        artist: Artist,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCE, toJSONObject(artist), responseListener, errorListener)
    }

    private fun toJSONObject(artist: Artist): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(artist)
        return JSONObject(jsonString)
    }

}