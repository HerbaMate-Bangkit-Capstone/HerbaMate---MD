package com.herbamate.herbamate.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = false) val id: String,
    val herbalName: String,
    val herbalLatin: String,
    val herbalDescription: String,
    val herbalImage: String
) : Parcelable
