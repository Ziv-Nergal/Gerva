package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R
import java.util.*

data class Button(
    val title: String,
    val subtitle: String? = null,
    val mainIcon: Int,
    val secondaryIcon: Int? = null
) : Model {

    override val id: String = UUID.randomUUID().toString()

    interface Listener {
        fun onButtonClicked(button: Button)
    }

    override fun getViewType(): Int = R.layout.item_button
}