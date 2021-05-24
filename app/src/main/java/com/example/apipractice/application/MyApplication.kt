package com.example.apipractice.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.example.apipractice.data.ProfileData


//TODO Make All Data Classes Parcelable and use @Keep Annotation (if not used)
class MyApplication : Application() {

    /** Running Activity Instance */
    lateinit var currentActivity: AppCompatActivity

    /** Login Token - Used in API Authorization */
    private var loginToken: String = ""

    /** Logged In User Type */
    private var loginUserType: String? = ""

    /** LoggedIn User Profile Data */
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

    /**
     * @param token Store Token
     * */
    fun setToken(token: String?) {
        loginToken = token ?: ""
    }

    /**
     * @param user Store User
     * */
    fun setUserType(user: String?) {
        loginUserType = user ?: ""
    }

    /**
     * @return Token
     * */
    fun getToken() = loginToken

    /**
     * @param profileData Store Profile data
     * */
    fun setProfileData(profileData: ProfileData) {
        userProfileData = profileData
    }

    /**
     * @return Profile Data
     * */
    fun getProfileData() = userProfileData
}