package com.herbamate.herbamate.data.remote.response

import com.google.gson.annotations.SerializedName

data class HerbDetailResponse(

	@field:SerializedName("image_link")
	val imageLink: String? = null,

	@field:SerializedName("disease")
	val disease: List<String>? = null,

	@field:SerializedName("latin_name")
	val latinName: String? = null,

	@field:SerializedName("composition")
	val composition: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("local_name")
	val localName: List<String>? = null
)
