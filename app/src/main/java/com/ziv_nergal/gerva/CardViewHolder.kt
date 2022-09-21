package com.ziv_nergal.gerva

import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.databinding.ItemCardViewBinding
import com.ziv_nergal.gerva.model.Card

class CardViewHolder(
    override val binding: ItemCardViewBinding
) : GenericRecyclerViewAdapter.GenericViewHolder(binding) {

    override fun bind(model: Model?) {
        super.bind(model)
        binding.cardView.setOnClickListener { flipCard() }
    }

    private fun flipCard() {
        itemView.animate().scaleY(0f).withEndAction {
            binding.cardContent.rotation += 180f
            itemView.animate().scaleY(1f).withEndAction {
                (listener as? Card.Listener)?.onCardFlipped()
            }
        }
    }
}