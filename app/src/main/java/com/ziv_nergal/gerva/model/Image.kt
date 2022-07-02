package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R

data class Image(val src: Int) : Model {

    override fun getViewType(): Int = R.layout.item_image

    override fun isEqualTo(other: Model?) = src == (other as Image).src
}