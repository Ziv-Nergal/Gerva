package com.ziv_nergal.gerva.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

class BindingUtils {

    companion object {

        @BindingAdapter("bind:srcImg")
        @JvmStatic
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }
}