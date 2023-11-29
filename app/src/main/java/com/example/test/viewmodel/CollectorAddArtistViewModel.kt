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
import com.example.test.repository.AlbumRepository
import com.example.test.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorAddArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).artistDao()
    )

    private val albumRepository = AlbumRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumDao()
    )

    var name = MutableLiveData<String>()
    var errorName = MutableLiveData<String>()
    var isValidName = MutableLiveData<Boolean>()

    var image = MutableLiveData<String>()
    var errorImage = MutableLiveData<String>()
    var isValidImage = MutableLiveData<Boolean>()

    var birthDate = MutableLiveData<String>()
    var errorBirthDate = MutableLiveData<String>()
    var isValidBirthDate = MutableLiveData<Boolean>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()
    var isValidDescription = MutableLiveData<Boolean>()

    var error = MutableLiveData<Boolean>()

    var listaAlbumes = MutableLiveData<List<Album>>()
    var albumPosition = MutableLiveData<Int>()

    fun addArtist() {
        if (
            isValidName.value == true
            && isValidImage.value == true
            && isValidBirthDate.value == true
            && isValidDescription.value == true
        ) {
            val artist = Artist(
                null,
                name.value,
                image.value,
                description.value,
                birthDate.value
            )
            try {
                viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        val artistCreated = artistRepository.create(artist)
                        albumRepository.albumWithArtist(
                            listaAlbumes.value?.get(albumPosition.value!!)?.id,
                            artistCreated.id
                        )
                        error.postValue(false)
                    }
                }

            } catch (exception: Exception) {
                error.postValue(true)
            }
        }
    }

    fun obtenerAlbumes() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    listaAlbumes.postValue(albumRepository.getAll())
                }
            }
        } catch (exception: Exception) {
            error.postValue(true)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorAddArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorAddArtistViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}