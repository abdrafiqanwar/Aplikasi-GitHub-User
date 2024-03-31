package com.example.submissionawal.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionawal.database.FavoriteUser
import com.example.submissionawal.database.FavoriteUserDao
import com.example.submissionawal.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()
    fun getFavoriteUserByUsername(name: String): LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(name)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun update(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.update(favoriteUser) }
    }
}