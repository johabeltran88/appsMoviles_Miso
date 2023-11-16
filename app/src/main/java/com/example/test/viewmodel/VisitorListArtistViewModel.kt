package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.model.Artist
import com.example.test.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitorListArtistViewModel(application: Application) : AndroidViewModel(application) {


    private val artistRepository = ArtistRepository(application)

    // LiveData to hold the list of artist.
    val artists = MutableLiveData<List<Artist>>()

    // LiveData to hold any error state.
    val error = MutableLiveData<String>()

    // Function to fetch all albums and post the value to the LiveData.
    fun fetchAllArtists() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    artists.postValue(artistRepository.getAll())
                }
            }
        } catch (exception: Exception) {
            error.postValue("Failed to fetch artists: ${exception.message}")
        }
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