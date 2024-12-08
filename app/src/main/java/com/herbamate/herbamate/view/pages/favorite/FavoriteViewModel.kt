package com.herbamate.herbamate.view.pages.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herbamate.herbamate.data.local.entity.Favorite
import com.herbamate.herbamate.repository.FavoriteRepository
import com.herbamate.herbamate.utils.Result

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _favoriteResult = MutableLiveData<Result<List<Favorite>>>()
    val favoriteResult: LiveData<Result<List<Favorite>>> = _favoriteResult

    fun fetchFavorite() {
        _favoriteResult.value = Result.Loading
        favoriteRepository.getAllFavorite().observeForever { favorites ->
            if (favorites.isNotEmpty()) {
                _favoriteResult.value = Result.Success(favorites)
            } else {
                _favoriteResult.value = Result.Success(emptyList())
            }
        }
    }

    fun insertFavorite(favorite: Favorite) {
        favoriteRepository.insert(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        favoriteRepository.delete(favorite)
    }

    fun getFavorite(id: Int): LiveData<Favorite> {
        return favoriteRepository.getFavoriteById(id)
    }
}