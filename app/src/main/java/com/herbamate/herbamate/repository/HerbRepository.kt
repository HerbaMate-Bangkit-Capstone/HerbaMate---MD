package com.herbamate.herbamate.repository

import com.herbamate.herbamate.data.remote.HerbAPI
import com.herbamate.herbamate.data.remote.response.BasicResDto
import com.herbamate.herbamate.data.remote.response.HerbDetailResponse
import com.herbamate.herbamate.data.remote.response.HerbResponse
import com.herbamate.herbamate.model.Herb

class HerbRepository (private val api : HerbAPI) {
    suspend fun getAllHerbs () : BasicResDto<List<HerbResponse>>  {
        return api.getAllHerbs()
    }

    suspend fun getHerbById (id : Int) : BasicResDto<HerbDetailResponse>{
        return  api.getHerbDetail(id)
    }
}