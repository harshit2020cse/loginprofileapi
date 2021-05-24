package com.example.apipractice.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class BannerListModel(
    @SerializedName("status") var status: Boolean,
    val `data`: List<Data>?
) : Parcelable