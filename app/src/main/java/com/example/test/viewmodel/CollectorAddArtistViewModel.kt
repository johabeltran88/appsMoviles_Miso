package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.common.validateDate
import com.example.test.common.validateValue
import com.example.test.model.Artist
import com.example.test.network.NetworkAdapterService
import com.example.test.repository.ArtistRepository

class CollectorAddArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)

    var name = MutableLiveData<String>()
    var errorName = MutableLiveData<String>()

    var image = MutableLiveData<String>()
    var errorImage = MutableLiveData<String>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()

    var birthDate = MutableLiveData<String>()
    var errorBirthDate = MutableLiveData<String>()

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

    fun validateDescription() {
        valid.value = true
        errorDescription.value = validateValue(description.value, 500)
    }

    fun validateBirthDate() {
        valid.value = true
        errorBirthDate.value = validateDate(birthDate.value)
    }

    fun addArtist() {
        validateName()
        validateImage()
        validateDescription()
        validateBirthDate()
        if (valid.value == true) {
            val artist = Artist(
                null,
                name.value,
                image.value,
                description.value,
                birthDate.value
            )
            artistRepository.create(artist, {
                error.value = false
            }, {
                error.value = true
            })
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