package com.example.apipractice.repo

import com.example.apipractice.data.LoginModel
import com.example.apipractice.data.ProfileModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    /** Service URL Path */
    companion object {
        const val PATH = "auth/"
    }

    /** Check Login Data */
    @POST("${PATH}login/medoplus")
    fun userLogin(@Body jsonObject: JsonObject): Call<LoginModel>

    /** Check Login Data */
    @GET("${PATH}patient/profile")
    fun getProfile(): Call<ProfileModel>
}