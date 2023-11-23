package com.example.test.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.test.database.CommentDao
import com.example.test.model.Comment
import com.example.test.network.NetworkAdapterService

class CommentRepository(private val application: Application, private val commentDao: CommentDao) {

    suspend fun create(albumId: Int?, comment: Comment) {
        NetworkAdapterService.getInstance(application).createComment(albumId, comment)
        commentDao.deleteAllByAlbumId(albumId)
    }

    suspend fun getAll(albumId: Int?): List<Comment> {
        val cached = commentDao.findAllByAlbumId(albumId)
        return cached.ifEmpty {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            ) {
                val comments = NetworkAdapterService.getInstance(application).getComments(albumId)
                commentDao.insertAll(comments)
                comments
            } else {
                emptyList()
            }
        }
    }

}