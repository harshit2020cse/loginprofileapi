package com.example.apipractice.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex


class MyApplication : Application() {

    lateinit var currentActivity: AppCompatActivity

    private var mInstance: MyApplication? = null

    private var loginToken: String = ""

    private var loginUserType: String? = ""


    @Synchronized
    fun getApplication(): MyApplication? {
        return mInstance
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

    fun getUserType() = loginUserType

}