package com.example.test.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.test.model.Artist
import com.example.test.network.NetworkAdapterService
import org.json.JSONObject

class ArtistRepository(private val application: Application) {

    fun create(
        artist: Artist,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        NetworkAdapterService.getInstance(application).createArtist(artist, onComplete, onError)
    }

    fun getAll(
        onComplete: (resp: List<Artist>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        NetworkAdapterService.getInstance(application).getArtists(onComplete, onError)
    }

}