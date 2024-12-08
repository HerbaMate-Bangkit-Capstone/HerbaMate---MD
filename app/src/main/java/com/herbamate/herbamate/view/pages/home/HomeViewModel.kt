package com.herbamate.herbamate.view.pages.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.herbamate.herbamate.model.Herb
import com.herbamate.herbamate.repository.HerbRepository
import com.herbamate.herbamate.utils.Result
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(
    private val herbRepository: HerbRepository
) : ViewModel() {

    private val _herb = MutableLiveData<Result<List<Herb>>>()
    val herb: LiveData<Result<List<Herb>>>
        get() = _herb

    fun getAllHerb() {
        viewModelScope.launch {
            try {
                _herb.postValue(Result.Loading)
                val result = herbRepository.getAllHerbs()
                result.data?.let { data ->
                    _herb.postValue(Result.Success(data.map {
                        Herb(
                            id = it.id ?: 0,
                            name = it.name ?: "No Name Provides",
                            latinName = it.latinName ?: "No Latin Name Provides",
                            imageLink = it.imageLink ?: "https://i.stack.imgur.com/l60Hf.png",
                            description = it.description ?: ""
                        )
                    }))
                }

            } catch (e: HttpException) {
                _herb.postValue(Result.Error(e.message.toString()))
            }
        }
    }

}