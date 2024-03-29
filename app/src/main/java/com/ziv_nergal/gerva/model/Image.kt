package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R
import java.util.*

data class Image(val src: Int) : Model {

    override val id: String = UUID.randomUUID().toString()

    override fun getLayoutId(): Int = R.layout.item_image
}