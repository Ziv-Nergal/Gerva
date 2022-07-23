package com.ziv_nergal.genericrecyclerviewadapter

/**
 * All items in the recyclerView will be represented by this interface.
 * This will allow each viewHolder being created to get it's layout resource id and
 * compare it with other viewHolders for better performance.
 */
interface Model {

    /**
     * Gets the resource id from the model class.
     *
     * @return A layout resource id.
     */
    fun getViewType(): Int

    /**
     * Helps comparing models by their content.
     *
     * @return true if models have identical content, otherwise false.
     */
    fun isEqualTo(other: Model?): Boolean = this == other
}