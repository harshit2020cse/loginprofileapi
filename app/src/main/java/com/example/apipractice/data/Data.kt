package com.example.apipractice.data

import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Keep
data class Data(
    val __v: Int?,
    val _id: String?,
    val createdAt: String?,
    val type: String?,
    val updatedAt: String?,
    val url: String?,
    val urls: List<Lan>?
) : Parcelable


@Parcelize
data class Lan(
    val en: String?
) : Parcelable