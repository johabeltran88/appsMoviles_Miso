package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.common.validateEmailValue
import com.example.test.common.validateSpinner
import com.example.test.common.validateValue
import com.example.test.model.Collector
import com.example.test.model.Comment
import com.example.test.repository.CollectorRepository
import com.example.test.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCommentToAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val collectorRepository = CollectorRepository(application)
    private val commentRepository = CommentRepository(application)

    var albumId = MutableLiveData<Int>()

    var name = MutableLiveData<String>()
    var errorName = MutableLiveData<String>()

    var email = MutableLiveData<String>()
    var errorEmail = MutableLiveData<String>()

    var telephone = MutableLiveData<String>()
    var errorTelephone = MutableLiveData<String>()

    var rating = MutableLiveData<String>()
    var errorRating = MutableLiveData<String>()

    var description = MutableLiveData<String>()
    var errorDescription = MutableLiveData<String>()

    var error = MutableLiveData<Boolean>()
    var valid = MutableLiveData<Boolean>()

    fun validateName() {
        valid.value = true
        errorName.value = validateValue(name.value, 50)
    }

    fun validateEmail() {
        valid.value = true
        errorEmail.value = validateEmailValue(email.value)
    }

    fun validateTelephone() {
        valid.value = true
        errorTelephone.value = validateValue(telephone.value, 50)
    }

    private fun validateRating() {
        valid.value = true
        errorRating.value = validateSpinner(rating.value, "Puntuación")
    }

    fun validateDescription() {
        valid.value = true
        errorDescription.value = validateValue(description.value, 500)
    }

    fun addComment() {
        if (valid.value == true) {
            validateName()
        }
        if (valid.value == true) {
            validateEmail()
        }
        if (valid.value == true) {
            validateTelephone()
        }
        if (valid.value == true) {
            validateRating()
        }
        if (valid.value == true) {
            validateDescription()
        }
        if (valid.value == true) {
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