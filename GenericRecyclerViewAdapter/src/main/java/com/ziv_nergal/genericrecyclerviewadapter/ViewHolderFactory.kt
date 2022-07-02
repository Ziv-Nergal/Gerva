package com.ziv_nergal.genericrecyclerviewadapter

import android.view.ViewGroup

interface ViewHolderFactory<Listener> {
    fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : GenericRecyclerViewAdapter.GenericViewHolder<Listener>?
}