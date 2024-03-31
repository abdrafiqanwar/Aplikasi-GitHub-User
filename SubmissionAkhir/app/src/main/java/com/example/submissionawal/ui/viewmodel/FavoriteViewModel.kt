package com.example.submissionawal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.database.FavoriteUser
import com.example.submissionawal.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
    fun getFavoriteUserByUsername(name: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(name)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun update(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.update(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
}