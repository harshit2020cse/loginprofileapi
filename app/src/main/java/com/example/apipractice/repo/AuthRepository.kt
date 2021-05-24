package com.example.apipractice.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apipractice.data.BannerListModel
import com.example.apipractice.data.LoginModel
import com.example.apipractice.data.ProfileModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

//    /** Get User Login API Response Data */
//    fun userLogin(jsonObject: JsonObject): LiveData<LoginModel> {
//        val loginResponse = MutableLiveData<LoginModel>()
//        val responseData = MutableLiveData<String>()
//        AuthApiService().userLogin(jsonObject)
//            .enqueue(object : Callback<LoginModel> {
//                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
//                    Log.e("Response", "${response.body()}")
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            loginResponse.value = response.body()
//                        }
//                    } else {
//                        responseData.value = response.errorBody().toString()
//                    }
//                }
//                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
//                    responseData.value = t.message.toString()
//                }
//            })
//        return loginResponse
//    }

//    /** Get Profile API Response Data */
//    fun getProfile(): LiveData<ProfileModel> {
//        val loginResponse = MutableLiveData<ProfileModel>()
//        AuthApiService().getProfile()
//            .enqueue(object : Callback<ProfileModel> {
//                override fun onResponse(
//                    call: Call<ProfileModel>,
//                    response: Response<ProfileModel>
//                ) {
//                    Log.e("ProfileAPIResponse", "${response.body()}")
//                    if (response.isSuccessful) {
//                        loginResponse.value = response.body()
//                    }
//                }
//                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
//                }
//            })
//        return loginResponse
//    }

//    /** Update User Profile API Response Data */
//    fun updateUserProfile(jsonObject: JsonObject): LiveData<ProfileModel> {
//        val profileResponse = MutableLiveData<ProfileModel>()
//        AuthApiService().updateUserProfile(jsonObject)
//            .enqueue(object : Callback<ProfileModel> {
//                override fun onResponse(
//                    call: Call<ProfileModel>,
//                    response: Response<ProfileModel>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            profileResponse.postValue(response.body())
//                        }
//                    }
//                }
//                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
//                }
//            })
//        return profileResponse
//    }

    /** Get Banner API Response Data */
    fun getBannerList(): LiveData<BannerListModel> {
        val bannerResponse = MutableLiveData<BannerListModel>()
        AuthApiService().getBannerList()
            .enqueue(object : Callback<BannerListModel> {
                override fun onResponse(
                    call: Call<BannerListModel>,
                    response: Response<BannerListModel>
                ) {
                    Log.e("BannerResponse", "${response.body()}")
                    if (response.isSuccessful) {
                        bannerResponse.value = response.body()
                    }
                }
                override fun onFailure(call: Call<BannerListModel>, t: Throwable) {
                }
            })
        return bannerResponse
    }

}