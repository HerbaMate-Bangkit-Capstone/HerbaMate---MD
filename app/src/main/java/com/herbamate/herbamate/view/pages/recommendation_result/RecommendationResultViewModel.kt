package com.herbamate.herbamate.view.pages.recommendation_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecommendationResultViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is recommendation result Activity"
    }
    val text: LiveData<String> = _text
}