package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.common.isURLValid
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
    var isValidName = MutableLiveData<Boolean>()

    var image = MutableLiveData<String>()
    var errorImage = MutableLiveData<String>()
    var isValidImage = MutableLiveData<Boolean>()

    var releaseDate = MutableLiveData<String>()
    var errorReleaseDate = MutableLiveData<String>()
    var isValidReleaseDate = MutableLiveData<Boolean>()

    var genre = MutableLiveData<String>()
    var errorGenre = MutableLiveData<String>()
    var isValidGenre = MutableLiveData<Boolean>()

    var recordLabel = MutableLiveData<String>()
    var errorRecordLabel = MutableLiveData<String>()
    var isValidRecordLabel = MutableLiveData<Boolean>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()
    var isValidDescription = MutableLiveData<Boolean>()

    var error = MutableLiveData<Boolean>()

    fun addAlbum() {
        if (
            isValidName.value == true
            && isValidImage.value == true
            && isValidReleaseDate.value == true
            && isValidGenre.value == true
            && isValidRecordLabel.value == true
            && isValidDescription.value == true
        ) {
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