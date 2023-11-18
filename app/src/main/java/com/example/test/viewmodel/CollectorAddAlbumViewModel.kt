package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.common.validateDate
import com.example.test.common.validateSpinner
import com.example.test.common.validateValue
import com.example.test.database.VinylRoomDatabase
import com.example.test.model.Album
import com.example.test.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorAddAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumDao()
    )

    var name = MutableLiveData<String>()
    var errorName = MutableLiveData<String>()

    var image = MutableLiveData<String>()
    var errorImage = MutableLiveData<String>()

    var releaseDate = MutableLiveData<String>()
    var errorReleaseDate = MutableLiveData<String>()

    var genre = MutableLiveData<String>()
    var errorGenre = MutableLiveData<String>()

    var recordLabel = MutableLiveData<String>()
    var errorRecordLabel = MutableLiveData<String>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()

    var error = MutableLiveData<Boolean>()
    var valid = MutableLiveData<Boolean>()

    fun validateName() {
        valid.value = true
        errorName.value = validateValue(name.value, 50)
    }

    fun validateImage() {
        valid.value = true
        errorImage.value = com.example.test.common.validateImage(image.value)
    }

    fun validateReleaseDate() {
        valid.value = true
        errorReleaseDate.value = validateDate(releaseDate.value)
    }

    private fun validateGenre() {
        valid.value = true
        errorGenre.value = validateSpinner(genre.value, "Género")
    }

    private fun validateRecordLabel() {
        valid.value = true
        errorRecordLabel.value = validateSpinner(recordLabel.value, "Disquera")
    }

    fun validateDescription() {
        valid.value = true
        errorDescription.value = validateValue(description.value, 500)
    }

    fun addAlbum() {
        if (valid.value == true) {
            validateName()
        }
        if (valid.value == true) {
            validateImage()
        }
        if (valid.value == true) {
            validateGenre()
        }
        if (valid.value == true) {
            validateRecordLabel()
        }
        if (valid.value == true) {
            validateReleaseDate()
        }
        if (valid.value == true) {
            validateDescription()
        }
        if (valid.value == true) {
            val album = Album(
                null,
                name.value,
                image.value,
                releaseDate.value,
                description.value,
                genre.value,
                recordLabel.value
            )
            try {
                viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        albumRepository.create(album)
                        error.postValue(false)
                    }
                }
            } catch (exception: Exception) {
                error.postValue(true)
            }
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorAddAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorAddAlbumViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}