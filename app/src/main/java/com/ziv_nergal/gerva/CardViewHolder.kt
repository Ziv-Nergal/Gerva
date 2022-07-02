package com.ziv_nergal.gerva

import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.genericrecyclerviewadapter.ViewHolderClickCallback
import com.ziv_nergal.gerva.databinding.ItemCardViewBinding
import com.ziv_nergal.gerva.model.Card

class CardViewHolder<Listener>(private val binding: ItemCardViewBinding) :
    GenericRecyclerViewAdapter.GenericViewHolder<Listener>(binding) {

    private var listener: Card.Listener? = null

    override fun bind(
        model: Model?,
        listener: Listener?,
        onViewHolderClicked: ViewHolderClickCallback?
    ) {
        super.bind(model, listener, onViewHolderClicked)
        this.listener = listener as? Card.Listener
        itemView.setOnClickListener { flipCard() }
    }

    private fun flipCard() {
        itemView.animate().scaleY(0f).withEndAction {
            binding.cardContent.rotation += 180f
            itemView.animate().scaleY(1f).withEndAction {
                listener?.onCardFlipped()
            }
        }
    }
}