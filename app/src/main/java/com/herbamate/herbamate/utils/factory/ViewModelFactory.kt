package com.herbamate.herbamate.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herbamate.herbamate.di.Injection
import com.herbamate.herbamate.repository.FavoriteRepository
import com.herbamate.herbamate.repository.HerbRepository
import com.herbamate.herbamate.repository.RecommendationRepository
import com.herbamate.herbamate.view.pages.detail.DetailViewModel
import com.herbamate.herbamate.view.pages.favorite.FavoriteViewModel
import com.herbamate.herbamate.view.pages.home.HomeViewModel
import com.herbamate.herbamate.view.pages.recommendation_result.RecommendationResultViewModel
import com.herbamate.herbamate.view.pages.result.ResultViewModel

class ViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val herbRepository: HerbRepository,
    private val recommendationRepository: RecommendationRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(herbRepository, favoriteRepository) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(herbRepository) as T
        }

        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(herbRepository) as T
        }

        if (modelClass.isAssignableFrom(RecommendationResultViewModel::class.java)) {
            return RecommendationResultViewModel(recommendationRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                favoriteRepository = Injection.provideFavoriteRepository(context),
                herbRepository = Injection.providesHerbRepository(),
                recommendationRepository = Injection.providesRecommendationRepository()
            )
        }.also { instance = it }
    }
}