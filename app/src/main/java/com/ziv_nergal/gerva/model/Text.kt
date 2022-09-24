package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R
import java.util.*

data class Text(
    val text: String
) : Model {

    interface Listener {
        fun onTextViewClicked(model: Text)
    }

    override val id: String = UUID.randomUUID().toString()

    override fun getLayoutId(): Int = R.layout.item_text
}