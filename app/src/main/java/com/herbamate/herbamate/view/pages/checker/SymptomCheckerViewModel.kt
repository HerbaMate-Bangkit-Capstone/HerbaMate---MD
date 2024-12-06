package com.herbamate.herbamate.view.pages.checker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SymptomCheckerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is symptom checker Activity"
    }
    val text: LiveData<String> = _text
}