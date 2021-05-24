package com.example.apipractice.view.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.application.AppConstant
import com.example.apipractice.base.BaseCommonAdapter
import com.example.apipractice.data.BannerImage
import com.example.apipractice.data.BannerListModel
import com.example.apipractice.repo.AuthApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVM : ViewModel() {

    /* Data List */
    var bannerAdapterList: ArrayList<BannerHomeItemViewModel> = ArrayList()

    /* Adapter */
    var bannerAdapter: BaseCommonAdapter<BannerHomeItemViewModel>? = null
    val responseData = MutableLiveData<String>()

    val apiResponse = MutableLiveData<BannerListModel>()


    /**
     * Get Banner List
     * */
    fun getBannerList() {

        AuthApiService().getBannerList()
            .enqueue(object : Callback<BannerListModel> {
                override fun onResponse(
                    call: Call<BannerListModel>,
                    response: Response<BannerListModel>
                ) {
                    Log.e("BannerResponse", "${response.body()}")
                    if (response.isSuccessful) {

                        if (response.body()?.status == true && response.body() != null) {
                            Log.e("Status", "Status ${response.body()?.status}")
                            apiResponse.postValue(response.body())
                            viewModelScope.launch(Dispatchers.IO) {

                                bannerAdapterList.clear()

                                response.body()?.data?.forEach { it1 ->
                                    if (it1._id == AppConstant.BANNER_TYPE.HOME) {
                                        it1.urls?.forEach {
                                            bannerAdapterList.add(
                                                BannerHomeItemViewModel(
                                                    BannerImage(imageUrl = it.en)
                                                )
                                            )
                                        }
                                    }
                                }
                                Log.e("ADAPTER", "ADAPTER : " + bannerAdapterList.size)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<BannerListModel>, t: Throwable) {
                    responseData.postValue(t.message.toString())
                }
            })
//        /* Notify Loading */
//        progressBarVisible.set(true)
//
//        /* Get API Response */
//        val bannerListResponse = AuthRepository().getBannerList()
//        bannerListener?.onSuccess(bannerListResponse)
//
//        /* Notify Loading */
//        progressBarVisible.set(false)
    }

}