package com.herbamate.herbamate.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herbamate.herbamate.di.Injection
import com.herbamate.herbamate.repository.FavoriteRepository
import com.herbamate.herbamate.view.pages.detail.DetailViewModel
import com.herbamate.herbamate.view.pages.favorite.FavoriteViewModel

class ViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                favoriteRepository = Injection.provideRepository(context)
            )
        }.also { instance = it }
    }
}