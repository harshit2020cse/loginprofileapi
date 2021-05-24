package com.example.apipractice.view.fragments.login

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.application.MyApplication
import com.example.apipractice.base.BaseModel
import com.example.apipractice.data.LoginModel
import com.example.apipractice.di.ResourceProvider
import com.example.apipractice.repo.AuthApiService
import com.example.apipractice.util.StorePreferences
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginVM : ViewModel() {

    /* Application to get context */
    val app = MyApplication.getApplication()

    /* ResourceProvider to get drawables */
    private val resourceProvider = ResourceProvider(MyApplication.getApplication())

    /* StorePreferences to Store Data */
    var storePreferences = StorePreferences(MyApplication.getApplication())

    /* UI Fields */
    val usernameField = ObservableField("PZZ0ZA00045")
    val passwordField = ObservableField("12345678")
    val isValidUsername = ObservableField(BaseModel(true))
    val isValidPassword = ObservableField(BaseModel(true))
    val progressBarVisible = ObservableBoolean(false)
    val loginResponse = MutableLiveData<LoginModel>()

    /** Login Button Click*/
    fun setLogin() {

        /* Check Username */
        if (usernameField.get()?.trim().isNullOrEmpty()) {
            /* Notify User */
            isValidUsername.set(
                BaseModel(
                    message = resourceProvider.getString(R.string.please_enter_medoID)
                )
            )
            /* To Prevent From BackStack Issue */
            isValidPassword.set(BaseModel(true))
            return
        }

        usernameField.set(usernameField.get()?.trim())

        /* Check Password */
        if (passwordField.get().isNullOrEmpty()) {
            /* Notify User */
            isValidPassword.set(
                BaseModel(
                    message = resourceProvider.getString(R.string.please_enter_password)
                )
            )
            /* To Prevent From BackStack Issue */
            isValidUsername.set(BaseModel(true))
            return
        }

        usernameField.set(usernameField.get()?.trim())

        /* POST Request Body Parameters */
        val sessionJsonObject = JsonObject()
        sessionJsonObject.addProperty(
            AppConstant.API_PARAM_KEY.DEVICE,
            AppConstant.API_PARAM_KEY.ANDROID
        )
        sessionJsonObject.addProperty(
            AppConstant.API_PARAM_KEY.FCM_TOKEN,
            AppConstant.API_PARAM_KEY.FCM_TOKEN
        )
        sessionJsonObject.addProperty(
            AppConstant.API_PARAM_KEY.DEVICE_ID,
            AppConstant.API_PARAM_KEY.DEVICE_ID
        )
        sessionJsonObject.addProperty(AppConstant.API_PARAM_KEY.NOTIFICATIONS_ENABLED, true)

        val jsonObject = JsonObject()
        jsonObject.addProperty(
            AppConstant.API_PARAM_KEY.USERNAME,
            usernameField.get()?.trim() ?: ""
        )
        jsonObject.addProperty(AppConstant.API_PARAM_KEY.PASSWORD, passwordField.get() ?: "")
        jsonObject.add(AppConstant.API_PARAM_KEY.SESSION, sessionJsonObject)

        /* Get User Login API Response Data */
        val responseData = MutableLiveData<String>()
        AuthApiService().userLogin(jsonObject)
            .enqueue(object : Callback<LoginModel> {
                override fun onResponse(
                    call: Call<LoginModel>,
                    response: Response<LoginModel>
                ) {
                    Log.e("Response", "${response.body()}")
                    if (response.isSuccessful) {
                        if (response.body()?.status == true && response.body() != null) {
                            Log.e("Status", "Status ${response.body()?.status}")

                            loginResponse.postValue(response.body())

                            viewModelScope.launch(Dispatchers.IO) {

                                /* Store TOKEN in DataStore */
                                response.body()?.data?.token?.let { it1 ->
                                    storePreferences.storeValue(
                                        StorePreferences.TOKEN,
                                        it1
                                    )
                                }
                                app.setToken(response.body()?.data?.token)

                                /* Store USER_TYPE in DataStore */
                                response.body()?.data?.userType?.let { it1 ->
                                    storePreferences.storeValue(
                                        StorePreferences.USER_TYPE,
                                        it1
                                    )
                                }
                            }
                        }
                    } else {
                        responseData.postValue(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    responseData.postValue(t.message.toString())
                }
            })

    }
}