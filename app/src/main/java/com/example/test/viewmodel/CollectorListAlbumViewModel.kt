package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.database.VinylRoomDatabase
import com.example.test.model.Album
import com.example.test.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorListAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumDao()
    )

    // LiveData to hold the list of albums.
    val albums = MutableLiveData<List<Album>>()

    // LiveData to hold any error state.
    val error = MutableLiveData<String>()

    val albumId = MutableLiveData<Int>()

    // Function to fetch all albums and post the value to the LiveData.
    fun fetchAllAlbums() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    albums.postValue(albumRepository.getAll())
                }
            }
        } catch (exception: Exception) {
            error.postValue("Failed to fetch artists: ${exception.message}")
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorListAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorListAlbumViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}