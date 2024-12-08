package com.herbamate.herbamate.view.pages.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herbamate.herbamate.model.Herb
import com.herbamate.herbamate.repository.HerbRepository
import com.herbamate.herbamate.utils.Result
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class ResultViewModel (
    private val herbRepository: HerbRepository
) : ViewModel() {

    private val _herb = MutableLiveData<Result<List<Herb>>>()
    val herb: LiveData<Result<List<Herb>>>
        get() = _herb

    fun getHerbSearch (q : String){
        viewModelScope.launch {
            try {
                _herb.postValue(Result.Loading)
                val result = herbRepository.getSearch(q)
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
                val errorMessage = e.response()?.errorBody()
                errorMessage?.let {
                    try {
                        val jsonObject = JSONObject(it.charStream().readText())
                        if (jsonObject.getInt("code") == 404){
                            _herb.postValue(Result.Error("404"))
                        } else {
                            _herb.postValue(Result.Error(e.message.toString()))
                        }
                    } catch (e: JSONException) {
                        _herb.postValue(Result.Error("Tidak bisa mengkonversi json"))
                    }
                }
            }
        }
    }
}