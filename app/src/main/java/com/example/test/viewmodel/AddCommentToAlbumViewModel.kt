package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.database.VinylRoomDatabase
import com.example.test.model.Collector
import com.example.test.model.Comment
import com.example.test.repository.CollectorRepository
import com.example.test.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCommentToAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val collectorRepository = CollectorRepository(application)
    private val commentRepository = CommentRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).commentDao()
    )

    var albumId = MutableLiveData<Int>()

    var name = MutableLiveData<String>()
    var errorName = MutableLiveData<String>()
    var isValidName = MutableLiveData<Boolean>()

    var email = MutableLiveData<String>()
    var errorEmail = MutableLiveData<String>()
    var isValidEmail = MutableLiveData<Boolean>()

    var telephone = MutableLiveData<String>()
    var errorTelephone = MutableLiveData<String>()
    var isValidTelephone = MutableLiveData<Boolean>()

    var rating = MutableLiveData<String>()
    var errorRating = MutableLiveData<String>()
    var isValidRating = MutableLiveData<Boolean>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()
    var isValidDescription = MutableLiveData<Boolean>()

    var error = MutableLiveData<Boolean>()

    fun addComment() {
        if (
            isValidName.value == true
            && isValidEmail.value == true
            && isValidTelephone.value == true
            && isValidRating.value == true
            && isValidDescription.value == true
        ) {
            val collector = Collector(
                null,
                name.value,
                telephone.value,
                email.value
            )
            try {
                viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        val collectorCreated = collectorRepository.create(collector)
                        val comment = Comment(
                            null,
                            description.value,
                            rating.value?.toInt(),
                            albumId.value,
                            collectorCreated
                        )
                        commentRepository.create(albumId.value, comment)
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
            if (modelClass.isAssignableFrom(AddCommentToAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddCommentToAlbumViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}