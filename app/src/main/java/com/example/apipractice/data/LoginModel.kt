package com.example.apipractice.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class LoginModel(
    @SerializedName("status") var status: Boolean,
    @SerializedName("data") val `data`: LoginData?,
    @SerializedName("message") val message: String
) : Parcelable