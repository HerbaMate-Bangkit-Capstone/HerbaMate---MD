package com.herbamate.herbamate.data.remote

import com.herbamate.herbamate.data.remote.request.RecommendationRequest
import com.herbamate.herbamate.data.remote.response.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecommendationAPI {
    @POST("/herb/predict")
    suspend fun getRecommendation (
        @Body symptoms : RecommendationRequest
    ) : RecommendationResponse
}