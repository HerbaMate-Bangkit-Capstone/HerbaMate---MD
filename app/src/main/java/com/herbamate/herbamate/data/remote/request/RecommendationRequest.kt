package com.herbamate.herbamate.data.remote.request

import com.google.gson.annotations.SerializedName

data class RecommendationRequest(

	@field:SerializedName("symptoms")
	val symptoms: String? = null
)
