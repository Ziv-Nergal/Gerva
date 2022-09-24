package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R
import java.util.*

class Card : Model {

    interface Listener {
        fun onCardFlipped()
    }

    override val id: String = UUID.randomUUID().toString()

    override fun getLayoutId(): Int = R.layout.item_card_view
}