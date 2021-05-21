package com.example.apipractice.view.fragments.home

import com.example.apipractice.R
import com.example.apipractice.base.BaseViewModel
import com.example.apipractice.data.BannerImage

class BannerHomeItemViewModel(val bannerImage: BannerImage) : BaseViewModel() {

    /**
     * @return Layout file i.e R.layout.view_design
     */
    override val viewType: Int = R.layout.inflate_banner_layout

}