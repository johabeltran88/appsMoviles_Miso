package com.example.test.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.test.model.Album
import com.example.test.model.Artist
import com.example.test.model.Collector
import com.example.test.model.Comment
import com.example.test.webservice.AlbumWebService
import com.example.test.webservice.ArtistWebService
import com.example.test.webservice.CollectorWebService
import com.example.test.webservice.CommentWebService
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkAdapterService constructor(context: Context) {

    private val albumWebService = AlbumWebService()
    private val artistWebService = ArtistWebService()
    private val collectorWebService = CollectorWebService()
    private val commentWebService = CommentWebService()

    companion object {
        private var instance: NetworkAdapterService? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkAdapterService(context).also { instance = it }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getArtists() = suspendCoroutine<List<Artist>> { continuation ->
        requestQueue.add(artistWebService.getAll({ response ->
            val artists = mutableListOf<Artist>()
            val resp = JSONArray(response)
            var item: JSONObject?
            for (i in 0 until resp.length()) {
                item = resp.getJSONObject(i)
                artists.add(
                    i, Artist(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate")
                    )
                )
            }
            artists.sortBy { artist -> artist.name?.lowercase(Locale.ROOT) }
            continuation.resume(artists)
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { continuation ->
        requestQueue.add(albumWebService.getAll({ response ->
            val resp = JSONArray(response)
            val albums = mutableListOf<Album>()
            var item: JSONObject?
            for (i in 0 until resp.length()) {
                item = resp.getJSONObject(i)
                albums.add(
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
            albums.sortBy { album -> album.name?.lowercase(Locale.ROOT) }
            continuation.resume(albums)
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun createArtist(artist: Artist) = suspendCoroutine { continuation ->
        requestQueue.add(artistWebService.create(artist, { response ->
            continuation.resume(
                Artist(
                    id = response.getInt("id"),
                    name = response.getString("name"),
                    image = response.getString("image"),
                    description = response.getString("description"),
                    birthDate = response.getString("birthDate")
                )
            )
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun createAlbum(album: Album) = suspendCoroutine { continuation ->
        requestQueue.add(albumWebService.create(album, { response ->
            continuation.resume(
                Album(
                    id = response.getInt("id"),
                    name = response.getString("name"),
                    cover = response.getString("cover"),
                    recordLabel = response.getString("recordLabel"),
                    releaseDate = response.getString("releaseDate"),
                    genre = response.getString("genre"),
                    description = response.getString("description")
                )
            )
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun createCollector(collector: Collector): Collector =
        suspendCoroutine { continuation ->
            requestQueue.add(collectorWebService.create(collector, { response ->
                continuation.resume(
                    Collector(
                        id = response.getInt("id"),
                        name = response.getString("name"),
                        telephone = response.getString("telephone"),
                        email = response.getString("email")
                    )
                )
            }, {
                continuation.resumeWithException(it)
            }))
        }

    suspend fun createComment(albumId: Int?, comment: Comment) = suspendCoroutine { continuation ->
        requestQueue.add(commentWebService.create(albumId, comment, { response ->
            continuation.resume(
                Comment(
                    id = response.getInt("id"),
                    description = response.getString("description"),
                    rating = response.getInt("rating"),
                    collector = comment.collector
                )
            )
        }, {
            continuation.resumeWithException(it)
        }))
    }

}