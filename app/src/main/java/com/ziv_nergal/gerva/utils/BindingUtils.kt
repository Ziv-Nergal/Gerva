package com.ziv_nergal.gerva.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class BindingUtils {

    companion object {

        @BindingAdapter("bind:srcImg")
        @JvmStatic
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }

        @BindingAdapter("bind:visible")
        @JvmStatic
        fun setViewVisibility(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}