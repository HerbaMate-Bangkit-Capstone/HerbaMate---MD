package com.herbamate.herbamate.di

import android.content.Context
import com.herbamate.herbamate.data.local.room.FavoriteDatabase
import com.herbamate.herbamate.repository.FavoriteRepository
import com.herbamate.herbamate.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()

        val appExecutors = AppExecutors()

        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}