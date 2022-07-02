package com.ziv_nergal.genericrecyclerviewadapter

interface Model {
    fun getViewType(): Int
    fun isEqualTo(other: Model?): Boolean
}