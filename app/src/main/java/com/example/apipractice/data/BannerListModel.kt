package com.example.apipractice.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class BannerListModel(
    val `data`: List<Data>?
) : Parcelable