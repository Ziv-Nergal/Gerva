package com.ziv_nergal.gerva

import android.widget.TextView
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.databinding.ItemCardViewBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CardViewHolder(
    override val binding: ItemCardViewBinding
) : GenericRecyclerViewAdapter.GenericViewHolder(binding) {

    override fun bind(model: Model) {
        super.bind(model)
        binding.cardView.setOnClickListener { animateLetters() }
    }

    private fun animateLetters() {

        val letters: ArrayList<TextView> = arrayListOf(
            binding.animatedTextG,
            binding.animatedTextE,
            binding.animatedTextR,
            binding.animatedTextV,
            binding.animatedTextA,
        )

        var millis: Long = 0

        MainScope().launch {
            letters.forEach {
                delay(millis)
                millis += 50

                it.animate().scaleX(0f).withEndAction {
                    it.animate().scaleX(1f)
                }
            }
        }
    }
}