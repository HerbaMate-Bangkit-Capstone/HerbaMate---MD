package com.herbamate.herbamate.view.pages.recommendation_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herbamate.herbamate.model.HerbRecommendation
import com.herbamate.herbamate.repository.RecommendationRepository
import com.herbamate.herbamate.utils.Result
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RecommendationResultViewModel (
    private val recommendationRepository: RecommendationRepository
) : ViewModel() {

    private val _herb = MutableLiveData<Result<List<HerbRecommendation>>>()
    val herb: LiveData<Result<List<HerbRecommendation>>>
        get() = _herb


    fun getRecommendation (symptoms : List<String>) {
        viewModelScope.launch {
            try {
                _herb.postValue(Result.Loading)
                val result = recommendationRepository.getRecommendation(symptoms)
                result.resultData?.let { data ->
                    _herb.postValue(Result.Success(data.map {
                        HerbRecommendation(
                            herbs = it?.herbs ?: "No Name Provides",
                            imageLink = it?.imageLink ?: "https://i.stack.imgur.com/l60Hf.png",
                            latinName = it?.latinName ?: "No Latin Name Provides" ,
                            usageMethod = it?.usageMethod ?: ""
                        )
                    }))
                }

            } catch (e: HttpException) {
                _herb.postValue(Result.Error(e.message.toString()))
            }
        }
    }
}