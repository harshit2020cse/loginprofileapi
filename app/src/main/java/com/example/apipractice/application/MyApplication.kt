package com.example.apipractice.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.example.apipractice.data.ProfileData

//TODO Proper Commenting on Methods with Parameters
//TODO Make All Data Classes Parcelable and use @Keep Annotation (if not used)
class MyApplication : Application() {

    lateinit var currentActivity: AppCompatActivity

    private var loginToken: String = ""

    private var loginUserType: String? = ""

    private var userProfileData: ProfileData? = null


    companion object {

        lateinit var mInstance: MyApplication

        @Synchronized
        fun getApplication(): MyApplication {
            return mInstance
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                currentActivity = activity as AppCompatActivity
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity as AppCompatActivity
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = activity as AppCompatActivity
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity as AppCompatActivity
            }
        })

    }

    fun setToken(token: String?) {
        loginToken = token ?: ""
    }

    fun setUserType(user: String?) {
        loginUserType = user ?: ""
    }

    fun getToken() = loginToken

    fun getUserType() = loginUserType

    fun setProfileData(profileData: ProfileData) {
        userProfileData = profileData
    }

    fun getProfileData() = userProfileData
}