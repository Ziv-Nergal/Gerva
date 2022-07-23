package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R

class Card : Model {

    interface Listener {
        fun onCardFlipped()
    }

    override fun getViewType(): Int = R.layout.item_card_view
}