package com.example.test.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.test.model.Album
import com.example.test.network.NetworkAdapterService
import org.json.JSONObject
import java.util.Collections


class AlbumRepository(private val application: Application) {

    fun create(
        album: Album,
        onComplete: (resp: JSONObject) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        NetworkAdapterService.getInstance(application).createAlbum(album, onComplete, onError)
    }

    fun getAll(
        onComplete: (resp: List<Album>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        NetworkAdapterService.getInstance(application).getAlbums(
            onComplete = { albums ->
                // Sort the list of albums by album name
                val sortedAlbums = albums.sortedBy { it.name?.toLowerCase() }

                // Now, you can use the sorted list as needed

                // Call onComplete method with the sorted list
                onComplete(sortedAlbums)
            },
            onError = { error ->
                // Handle error
                onError(error)
            }
        )
    }

}