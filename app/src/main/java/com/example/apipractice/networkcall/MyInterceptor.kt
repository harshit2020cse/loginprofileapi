package com.example.apipractice.networkcall

import android.content.ContentValues.TAG
import android.util.Log
import com.example.apipractice.application.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val app = MyApplication.getApplication()
        Log.e(TAG, "TokenHeader ${app.getToken()}")
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept:", "application/json")
            .addHeader(
                "Authorization",
                "Bearer ${app.getToken()}"
            )
            .build()
        return chain.proceed(request)
    }
}