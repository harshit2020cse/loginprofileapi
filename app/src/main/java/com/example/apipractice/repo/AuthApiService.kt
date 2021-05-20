package com.example.apipractice.repo

import com.example.apipractice.data.BannerListModel
import com.example.apipractice.data.LoginModel
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.networkcall.MyInterceptor
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    /** Check Login Data */
    @POST("${PATH}login/medoplus")
    fun userLogin( @Body jsonObject: JsonObject): Call<LoginModel>

    /** Check Login Data */
    @GET("${PATH}patient/profile")
    fun getProfile(): Call<ProfileModel>

    /** Update profile User Data */
    @PUT("${PATH}patient/profile")
    fun updateUserProfile(@Body jsonObject: JsonObject): Call<ProfileModel>

    /** Get Banner List Data */
    @POST("cms/public/banner-listing")
    fun getBannerList(): Call<BannerListModel>

    companion object{
        const val PATH = "auth/"
        val client = OkHttpClient.Builder().apply {
            addInterceptor(MyInterceptor())
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