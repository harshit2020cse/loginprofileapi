package com.example.apipractice.view.fragments.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.application.MyApplication
import com.example.apipractice.base.BaseModel
import com.example.apipractice.di.ResourceProvider
import com.example.apipractice.networkcall.AuthListener
import com.example.apipractice.repo.AuthRepository
import com.google.gson.JsonObject

class LoginVM : ViewModel() {

    private val resourceProvider = ResourceProvider(MyApplication.getApplication())
    /* UI Fields */
    val usernameField = ObservableField("PAP12MA00031")
    val passwordField = ObservableField("12345678")
    val isValidUsername = ObservableField(BaseModel(true))
    val isValidPassword = ObservableField(BaseModel(true))
    val progressBarVisible = ObservableBoolean(false)
    var authListener: AuthListener? = null
    val app = MyApplication.getApplication()

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
            isValidPassword.set(BaseModel(true))
            return
        }

        usernameField.set(usernameField.get()?.trim())

        /* Check Password */
        if (passwordField.get().isNullOrEmpty()) {
            /* Notify User */
            isValidPassword.set(
                BaseModel(
                    message = resourceProvider.getString(R.string.invalid_password)
                )
            )
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

        /* Notify Loading */
        progressBarVisible.set(true)

        /* Get API Response */
        val loginResponse = AuthRepository().userLogin(jsonObject)
        authListener?.onSuccess(loginResponse)

        /* Notify Loading */
        progressBarVisible.set(false)
    }


}