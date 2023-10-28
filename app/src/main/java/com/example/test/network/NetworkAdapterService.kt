package com.example.test.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.example.test.model.Album
import com.example.test.model.Artist
import com.example.test.webservice.AlbumWebService
import com.example.test.webservice.ArtistWebService
import org.json.JSONArray
import org.json.JSONObject

class NetworkAdapterService constructor(context: Context) {

    private val albumWebService = AlbumWebService()
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

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(albumWebService.getAll({ response ->
            val resp = JSONArray(response)
            val list = mutableListOf<Album>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                list.add(
                    i, Album(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description")
                    )
                )
            }
            onComplete(list)
        }, {
            onError(it)
        }))
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

    fun createAlbum(
        album: Album,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {

        requestQueue.add(albumWebService.create(album, { response ->
            onComplete(response)
        }, {
            onError(it)
        }))
    }

}