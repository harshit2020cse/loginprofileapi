package com.example.apipractice.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginModel(
    @SerializedName("data") val `data`: LoginData?,
    @SerializedName("message") val message: String
)