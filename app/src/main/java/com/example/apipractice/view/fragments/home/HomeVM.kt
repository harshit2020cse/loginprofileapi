package com.example.apipractice.view.fragments.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.apipractice.networkcall.BannerListener
import com.example.apipractice.repo.AuthRepository

class HomeVM : ViewModel() {

    var bannerListener: BannerListener? = null
    val progressBarVisible = ObservableBoolean(false)

    /* Data List */
    var bannerAdapterList: ArrayList<BannerHomeItemViewModel> = ArrayList()

    /* Get API Response Data*/
    fun getBannerList() {
        progressBarVisible.set(true)
        val bannerListResponse = AuthRepository().getBannerList()
        bannerListener?.onSuccess(bannerListResponse)
        progressBarVisible.set(false)
    }

}