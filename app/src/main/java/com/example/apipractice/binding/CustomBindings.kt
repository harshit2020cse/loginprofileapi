package com.example.apipractice.binding

import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.apipractice.R
import com.example.apipractice.base.BaseModel

class CustomBindings {

    companion object {

        @BindingAdapter("imageurl")
        @JvmStatic
        fun bindImageView(view: AppCompatImageView, url: String) {
            Glide.with(view.context)
                .load(url)
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_account).centerCrop()
                        .transform(CenterCrop())
                )
                .into(view)
        }
    }
}


@BindingAdapter("setError")
fun EditText.setError(
    errorModel: BaseModel?
) {
    errorModel?.let {
        if (!it.status)
            error = it.message
    }
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean?) {
    if (isVisible != null) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}



