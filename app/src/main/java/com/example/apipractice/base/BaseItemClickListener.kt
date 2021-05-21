package com.example.apipractice.base

import android.view.View

interface BaseItemClickListener {
    fun onItemClick(view: View, value: BaseViewModel)
}