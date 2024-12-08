package com.herbamate.herbamate.data.remote.response


import com.google.gson.annotations.SerializedName

data class HerbResponse(

    @field:SerializedName("image_link")
    val imageLink: String? = null,

    @field:SerializedName("latin_name")
    val latinName: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
