package com.example.apipractice.view.fragments.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.networkcall.BannerListener
import com.example.apipractice.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeVM() : ViewModel() {

    var bannerListener: BannerListener? = null
    val progressBarVisible = ObservableBoolean(false)

    /* Data List */
    var bannerAdapterList: ArrayList<BannerHomeItemViewModel> = ArrayList()

    fun getBannerList() {
        progressBarVisible.set(true)
        val bannerListResponse = AuthRepository().getBannerList()
        bannerListener?.onSuccess(bannerListResponse)
        progressBarVisible.set(false)
    }

}