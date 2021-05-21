package com.example.apipractice.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class MetaData(
    val exists: Boolean?
) : Parcelable