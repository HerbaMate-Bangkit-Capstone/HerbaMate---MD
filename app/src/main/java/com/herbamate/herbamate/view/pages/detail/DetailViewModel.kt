package com.herbamate.herbamate.view.pages.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herbamate.herbamate.data.local.entity.Favorite
import com.herbamate.herbamate.model.Herb
import com.herbamate.herbamate.model.HerbDetail
import com.herbamate.herbamate.repository.FavoriteRepository
import com.herbamate.herbamate.repository.HerbRepository
import com.herbamate.herbamate.utils.Result
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel (
    private val herbRepository: HerbRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _herb = MutableLiveData<Result<HerbDetail>>()
    val herb: LiveData<Result<HerbDetail>>
        get() = _herb

    fun fetchDetailEvent(id: Int) {
        viewModelScope.launch {
            try {
                _herb.postValue(Result.Loading)
                val result = herbRepository.getHerbById(id)
                result.data?.let { data ->
                    _herb.postValue(Result.Success(HerbDetail(
                        imageLink = result.data.imageLink ?: "https://placehold.co/600x400",
                        name = result.data.name ?: "No Name provides",
                        disease = result.data.disease ?: emptyList(),
                        latinName = result.data.latinName ?: "No Latin Name Provides",
                        composition = result.data.composition ?: "No Composition",
                        description = result.data.description ?: "No Description",
                        id = result.data.id ?: -1,
                        localName = result.data.localName ?: emptyList(),
                    )))
                }
            } catch (e: HttpException) {
                _herb.postValue(Result.Error(e.message.toString()))
            }
        }
    }

    fun insertFavoriteHerb(favorite: Favorite) {
        favoriteRepository.insert(favorite)
    }

    fun deleteFavoriteHerb(favorite: Favorite) {
        favoriteRepository.delete(favorite)
    }

    fun getIsFavoriteStatus (id : Int) = favoriteRepository.getFavoriteById(id)
}