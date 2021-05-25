package com.example.apipractice.dialog

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DialogUiConfig(
    val title: String,
    val message: String,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val gravity: Int,
    val cancelable: Boolean
) : Parcelable