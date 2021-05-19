package com.example.apipractice.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apipractice.data.LoginModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    fun userLogin(jsonObject: JsonObject): LiveData<LoginModel> {
        val loginResponse = MutableLiveData<LoginModel>()
        val responsee = MutableLiveData<String>()

        AuthApiService().userLogin(jsonObject)
            .enqueue(object : Callback<LoginModel> {
                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                    Log.e("Response","${response.body()}")
                    if (response.isSuccessful) {
                        if (response.body() != null ) {
                            loginResponse.value = response.body()
                        }
                    } else {
                        responsee.value = response.errorBody().toString()
                    }
                }

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    responsee.value = t.message.toString()

                }

            })

        return loginResponse
    }

}