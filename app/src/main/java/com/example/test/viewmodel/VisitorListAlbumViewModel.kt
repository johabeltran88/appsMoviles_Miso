package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.model.Album
import com.example.test.repository.AlbumRepository

class VisitorListAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(application)

    // LiveData to hold the list of albums.
    val albums = MutableLiveData<List<Album>>()

    // LiveData to hold any error state.
    val error = MutableLiveData<String>()

    val albumId = MutableLiveData<Int>()

    // Function to fetch all albums and post the value to the LiveData.
    fun fetchAllAlbums() {
        albumRepository.getAll(
            onComplete = { albumList ->
                // Post the list of albums to the LiveData.
                albums.postValue(albumList)
            },
            onError = { volleyError ->
                // Post an error message to the LiveData.
                error.postValue("Failed to fetch albums: ${volleyError.message}")
            }
        )
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VisitorListAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VisitorListAlbumViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}