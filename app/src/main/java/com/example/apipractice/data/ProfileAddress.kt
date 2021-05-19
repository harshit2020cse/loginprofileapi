package com.example.apipractice.data

import androidx.annotation.Keep

@Keep
data class ProfileAddress(
    val line1: DataValue? = null,
    val line2: DataValue? = null,
    val state: DataValue? = null,
    val district: DataValue? = null,
    val block: DataValue? = null,
    val zipcode: String? = null,
    val geo: List<Double>? = null,
    val _id: String? = null,
    val stateCode: Long? = null,
    val stateCodeAlpha: String? = null,
    val districtCode: Long? = null
)