package com.herbamate.herbamate.view.pages.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SymptomCategoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is symptom category Activity"
    }
    val text: LiveData<String> = _text
}