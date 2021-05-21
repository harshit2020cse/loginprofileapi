package com.example.apipractice.view.fragments.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.apipractice.base.BaseCommonAdapter
import com.example.apipractice.networkcall.BannerListener
import com.example.apipractice.repo.AuthRepository

class HomeVM : ViewModel() {

    var bannerListener: BannerListener? = null
    private val progressBarVisible = ObservableBoolean(false)

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