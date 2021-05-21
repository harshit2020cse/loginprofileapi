package com.example.apipractice.view.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.apipractice.application.MyApplication
import com.example.apipractice.base.EventListener
import com.example.apipractice.di.ResourceProvider

class MainActivityVM : ViewModel() {

    val resourceProvider = ResourceProvider(MyApplication.getApplication())

    val eventListener = EventListener()

    /* ToolBar Title */
    val toolBarTitle = ObservableField("")

}