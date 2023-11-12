package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.model.Artist
import com.example.test.repository.ArtistRepository

class VisitorListArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)

    // LiveData to hold the list of artist.
    val artists = MutableLiveData<List<Artist>>()

    // LiveData to hold any error state.
    val error = MutableLiveData<String>()

    // Function to fetch all albums and post the value to the LiveData.
    fun fetchAllArtists() {
        artistRepository.getAll(
            onComplete = { artistList ->
                // Post the list of albums to the LiveData.
                artists.postValue(artistList)
            },
            onError = { volleyError ->
                // Post an error message to the LiveData.
                error.postValue("Failed to fetch artists: ${volleyError.message}")
            }
        )
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VisitorListArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VisitorListArtistViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}