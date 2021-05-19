package com.example.apipractice.networkcall

import androidx.lifecycle.LiveData
import com.example.apipractice.data.LoginModel

interface AuthListener {
    fun onSuccess(loginResponse: LiveData<LoginModel>)
}