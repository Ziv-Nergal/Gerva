package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R
import com.ziv_nergal.gerva.toDp
import java.util.UUID

data class VerticalSpacer(private val height: Int) : Model {
    override val id: String = UUID.randomUUID().toString()
    override fun getLayoutId(): Int = R.layout.item_spacer
    fun getHeight(): Int = height.toDp()
}