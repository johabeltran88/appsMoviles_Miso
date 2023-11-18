package com.example.test.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.test.database.AlbumDao
import com.example.test.model.Album
import com.example.test.network.NetworkAdapterService
import java.util.Locale


class AlbumRepository(private val application: Application, private val albumDao: AlbumDao) {

    suspend fun create(album: Album) {
        NetworkAdapterService.getInstance(application).createAlbum(album)
        albumDao.deleteAll()
    }

    suspend fun getAll(): List<Album> {
        val cached = albumDao.findAll()
        return if (cached.isEmpty()) {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            ) {
                val albums = NetworkAdapterService.getInstance(application).getAlbums()
                albumDao.insertAll(albums)
                return albums
            } else {
                emptyList()
            }
        } else {
            cached.sortedBy { album -> album.name?.lowercase(Locale.ROOT) }
        }
    }

}