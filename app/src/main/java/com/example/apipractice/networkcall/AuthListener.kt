package com.example.apipractice.networkcall

import androidx.lifecycle.LiveData
import com.example.apipractice.data.BannerListModel
import com.example.apipractice.data.LoginModel
import com.example.apipractice.data.ProfileModel

/** Use for Login API Success Response */
interface AuthListener {
    fun onSuccess(loginResponse: LiveData<LoginModel>)
}

/** Use for Profile API Success Response */
interface ProfileListener {
    fun onSuccess(profileResponseResponse: LiveData<ProfileModel>)
}

/** Banner API Success Response */
interface BannerListener {
    fun onSuccess(bannerListResponse: LiveData<BannerListModel>)
}