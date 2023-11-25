package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test.database.VinylRoomDatabase
import com.example.test.model.Album
import com.example.test.model.Comment
import com.example.test.repository.AlbumRepository
import com.example.test.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val commentRepository = CommentRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).commentDao()
    )
    private val albumRepository = AlbumRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumDao()
    )

    val comments = MutableLiveData<List<Comment>>()
    val error = MutableLiveData<Boolean>()
    val album = MutableLiveData<Album>()

    fun fetchAllComments(albumId: Int?) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    comments.postValue(commentRepository.getAll(albumId))
                    error.postValue(false)
                }
            }
        } catch (exception: Exception) {
            error.postValue(true)
        }
    }

    fun fetchAlbum(albumId: Int?) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    album.postValue(albumRepository.getById(albumId))
                    error.postValue(false)
                }
            }
        } catch (exception: Exception) {
            error.postValue(true)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}