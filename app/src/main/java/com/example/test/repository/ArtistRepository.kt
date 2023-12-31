package com.example.test.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import com.example.test.database.ArtistDao
import com.example.test.model.Album
import com.example.test.model.Artist
import com.example.test.network.NetworkAdapterService
import java.util.Locale

class ArtistRepository(private val application: Application, private val artistDao: ArtistDao) {

    suspend fun create(artist: Artist) : Artist {
        val artistCreated = NetworkAdapterService.getInstance(application).createArtist(artist)
        artistDao.deleteAll()
        return artistCreated
    }

    suspend fun getAll(): List<Artist> {
        val cached = artistDao.findAll()
        return if (cached.isEmpty()) {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            ) {
                val artists = NetworkAdapterService.getInstance(application).getArtists()
                artistDao.insertAll(artists)
                return artists
            } else {
                emptyList()
            }
        } else {
            cached.sortedBy { album -> album.name?.lowercase(Locale.ROOT) }
        }
    }

    suspend fun getArtistAlbums(artistId: Int?): List<Album> {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            ) {
                val albums = NetworkAdapterService.getInstance(application).getArtistAlbums(artistId)
                return albums
            } else {
                return emptyList()
            }
    }
    suspend fun getById(artistId: Int?): Artist? {
        val cached = artistDao.findById(artistId)
        return if (cached == null) {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            ) {
                val artist = NetworkAdapterService.getInstance(application).getArtist(artistId)
                artist
            } else {
                null
            }
        } else {
            cached
        }
    }

}