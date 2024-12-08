package com.herbamate.herbamate.data.remote

import com.herbamate.herbamate.data.remote.response.BasicResDto
import com.herbamate.herbamate.data.remote.response.HerbDetailResponse
import com.herbamate.herbamate.data.remote.response.HerbResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface HerbAPI {

    @GET("/herb")
    suspend fun getAllHerbs() : BasicResDto<List<HerbResponse>>

    @GET("/herb/{id}")
    suspend fun getHerbDetail (
        @Path("id")  id : Int
    ) : BasicResDto<HerbDetailResponse>

    @GET("/herbs/search")
    suspend fun getSearchData (
        @Query("q") q : String
    ) : BasicResDto<List<HerbResponse>>
}