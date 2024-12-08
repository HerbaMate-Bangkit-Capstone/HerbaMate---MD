package com.herbamate.herbamate.model


data class HerbDetail (
    val imageLink: String,

    val disease: List<String>,

    val latinName: String,

    val composition: String,

    val name: String,

    val description: String,

    val id: Int,

    val localName: List<String>
)