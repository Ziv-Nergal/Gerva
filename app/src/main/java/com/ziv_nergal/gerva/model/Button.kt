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

    interface Listener {
        fun onButtonClicked(button: Button)
    }

    override val id: String = UUID.randomUUID().toString()

    override fun getLayoutId(): Int = R.layout.item_button
}