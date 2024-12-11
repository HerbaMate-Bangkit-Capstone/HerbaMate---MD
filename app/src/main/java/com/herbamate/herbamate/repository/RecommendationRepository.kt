package com.herbamate.herbamate.repository

import com.herbamate.herbamate.data.remote.RecommendationAPI
import com.herbamate.herbamate.data.remote.request.RecommendationRequest
import com.herbamate.herbamate.data.remote.response.RecommendationResponse

class RecommendationRepository(
    private val api: RecommendationAPI
) {
    suspend fun getRecommendation(symptoms: List<String>): RecommendationResponse {

        var raw = ""
        for (i in symptoms.indices) {
            if (i == (symptoms.size - 1)) {
                raw += symptoms[i]
                break
            }
            raw = raw + symptoms[i] + ", "
        }

        return api.getRecommendation(symptoms = RecommendationRequest(raw))
    }
}