package com.example.apipractice.view.fragments.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.apipractice.application.MyApplication
import com.example.apipractice.base.BaseCommonAdapter
import com.example.apipractice.di.ResourceProvider
import com.example.apipractice.networkcall.BannerListener
import com.example.apipractice.repo.AuthRepository

class HomeVM : ViewModel() {

    val resourceProvider = ResourceProvider(MyApplication.getApplication())

    var bannerListener: BannerListener? = null
    val progressBarVisible = ObservableBoolean(false)

    /* Data List */
    var bannerAdapterList: ArrayList<BannerHomeItemViewModel> = ArrayList()

    /* Adapter */
    var bannerAdapter: BaseCommonAdapter<BannerHomeItemViewModel>? = null

    /**
     * Get Banner List
     * */
    fun getBannerList() {

        /* Notify Loading */
        progressBarVisible.set(true)

        /* Get API Response */
        val bannerListResponse = AuthRepository().getBannerList()
        bannerListener?.onSuccess(bannerListResponse)

        /* Notify Loading */
        progressBarVisible.set(false)
    }

}