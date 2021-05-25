package com.example.apipractice.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.example.apipractice.BuildConfig

/**
 * Build intent that displays the App settings screen.
 * */
fun openAppSettings(context: Context) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
    intent.data = uri
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}