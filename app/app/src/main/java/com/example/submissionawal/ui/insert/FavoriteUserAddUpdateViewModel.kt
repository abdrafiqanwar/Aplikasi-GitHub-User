package com.example.submissionawal.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.submissionawal.database.FavoriteUser
import com.example.submissionawal.repository.FavoriteUserRepository

class FavoriteUserAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

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