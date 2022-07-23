package com.ziv_nergal.gerva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.genericrecyclerviewadapter.ViewHolderFactory

class ExampleViewHolderFactory : ViewHolderFactory {

    override fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewAdapter.GenericViewHolder? {

        return when (viewType) {
            R.layout.item_card_view ->
                CardViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                    )
                )
            else -> null
        }
    }
}