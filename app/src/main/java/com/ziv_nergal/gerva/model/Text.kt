package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R

data class Text(
    val text: String
) : Model {

    interface Listener {
        fun onTextViewClicked(model: Text)
    }

    override fun getViewType(): Int = R.layout.item_text

    override fun isEqualTo(other: Model?): Boolean = (other as? Text)?.text == this.text
}