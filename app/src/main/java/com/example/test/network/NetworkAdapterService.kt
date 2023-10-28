package com.example.test.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.example.test.model.Artist
import com.example.test.webservice.ArtistWebService
import org.json.JSONObject

class NetworkAdapterService constructor(context: Context) {

    private val artistWebService = ArtistWebService()

    companion object {
        private var instance: NetworkAdapterService? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkAdapterService(context).also { instance = it }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun createArtist(
        artist: Artist,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {

        requestQueue.add(artistWebService.create(artist, { response ->
            onComplete(response)
        }, {
            onError(it)
        }))
    }

}