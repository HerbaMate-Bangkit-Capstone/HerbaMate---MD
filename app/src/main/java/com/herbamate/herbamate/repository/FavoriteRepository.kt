package com.herbamate.herbamate.repository

import androidx.lifecycle.LiveData
import com.herbamate.herbamate.data.local.entity.Favorite
import com.herbamate.herbamate.data.local.room.FavoriteDao
import com.herbamate.herbamate.utils.AppExecutors

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao, private val appExecutors: AppExecutors
) {
    fun insert(favorite: Favorite) {
        appExecutors.diskIO.execute {
            favoriteDao.insertFavorite(favorite)
        }
    }

    fun delete(favorite: Favorite) {
        appExecutors.diskIO.execute {
            favoriteDao.deleteFavorite(favorite)
        }
    }

    fun getFavoriteById(id: String): LiveData<Favorite> {
        return favoriteDao.getFavoriteById(id)
    }

    fun getAllFavorite(): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao, appExecutors: AppExecutors
        ): FavoriteRepository = instance ?: synchronized(this) {
            instance ?: FavoriteRepository(favoriteDao, appExecutors)
        }.also { instance = it }
    }
}