package com.example.apipractice.view.fragments.home

import androidx.lifecycle.ViewModel
import com.example.apipractice.R
import com.example.apipractice.data.BannerImage

class BannerHomeItemViewModel(val bannerImage: BannerImage) : ViewModel() {

    /**
     * @return Layout file i.e R.layout.view_design
     */
    val viewType: Int = R.layout.inflate_banner_layout

}