package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.database.VinylRoomDatabase
import com.example.test.model.Album
import com.example.test.model.Artist
import com.example.test.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).artistDao()
    )

    val albums = MutableLiveData<List<Album>>()
    val error = MutableLiveData<Boolean>()
    val artist = MutableLiveData<Artist>()

    fun fetchAllAlbums(artistId: Int?) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    albums.postValue(artistRepository.getArtistAlbums(artistId))
                    error.postValue(false)
                }
            }
        } catch (exception: Exception) {
            error.postValue(true)
        }
    }

    fun fetchArtist(artistId: Int?) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    artist.postValue(artistRepository.getById(artistId))
                    error.postValue(false)
                }
            }
        } catch (exception: Exception) {
            error.postValue(true)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}