package com.ziv_nergal.gerva

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.genericrecyclerviewadapter.ViewHolderFactory
import com.ziv_nergal.gerva.databinding.ItemCardViewBinding

class ExampleViewHolderFactory : ViewHolderFactory {

    override fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewAdapter.GenericViewHolder? {

        return when (viewType) {
            R.layout.item_card_view ->
                CardViewHolder(
                    ItemCardViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else -> null
        }
    }
}