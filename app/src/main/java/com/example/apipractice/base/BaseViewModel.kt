package com.example.apipractice.base

import android.view.View

abstract class BaseViewModel {

    var clickListener: BaseItemClickListener? = null

    open fun onItemClick(view: View) {
        clickListener?.onItemClick(
            view, this
        )
    }

    /**
     * @return Layout file i.e R.layout.view_design
     */
    abstract val viewType: Int
}