package com.example.apipractice.data

import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Keep
data class BannerImage(
    val __v: Int? = null,
    val _id: String? = null,
    val type: String? = null,
    val url: String? = null,
    val imageUrl: String? = null
) : Parcelable