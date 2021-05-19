package com.example.apipractice.repo

import com.example.apipractice.data.LoginModel
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.networkcall.Intercepter
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    /** Check Login Data */
    @POST("${PATH}login/medoplus")
    fun userLogin( @Body jsonObject: JsonObject): Call<LoginModel>

    /** Check Login Data */
    @GET("${PATH}patient/profile")
    fun getProfile(): Call<ProfileModel>

    companion object{
        const val PATH = "auth/"
        val client = OkHttpClient.Builder().apply {
            addInterceptor(Intercepter())
        }.build()
        operator fun invoke() : AuthApiService {
            return Retrofit.Builder()
                .baseUrl("https://stage.api.medoplus.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthApiService::class.java)
        }
    }
}