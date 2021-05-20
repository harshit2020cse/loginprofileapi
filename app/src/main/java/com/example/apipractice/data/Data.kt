package com.example.apipractice.data

import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
data class Data(
    val __v: Int?,
    val _id: String?,
    val createdAt: String?,
    val type: String?,
    val updatedAt: String?,
    val url: String?,
    val urls: List<Lan>?
)

data class Lan(
    val en: String?,
    val hi: String?,
)