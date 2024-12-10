package com.herbamate.herbamate.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("result data")
	val resultData: List<ResultDataItem?>? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("prediction score")
	val predictionScore: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ResultDataItem(

	@field:SerializedName("image_link")
	val imageLink: String? = null,

	@field:SerializedName("latin_name")
	val latinName: String? = null,

	@field:SerializedName("usage_method")
	val usageMethod: String? = null,

	@field:SerializedName("herbs")
	val herbs: String? = null
)
