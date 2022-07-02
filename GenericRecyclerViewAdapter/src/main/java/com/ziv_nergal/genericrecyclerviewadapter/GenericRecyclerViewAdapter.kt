package com.ziv_nergal.genericrecyclerviewadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter.*

typealias ViewHolderClickCallback = (Model?, GenericViewHolder<*>) -> Unit

class GenericRecyclerViewAdapter<Listener>(
    items: List<Model>,
    private val listener: Listener? = null,
    private val viewHolderFactory: ViewHolderFactory<Listener>? = null,
    private val onViewHolderClicked: ViewHolderClickCallback? = null
) : RecyclerView.Adapter<GenericViewHolder<Listener>>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Model>() {

        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean =
            oldItem.isEqualTo(newItem)
    })

    init {
        updateData(items, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<Listener> =
        viewHolderFactory?.createViewHolder(parent, viewType) ?: GenericViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )


    override fun onViewRecycled(holder: GenericViewHolder<Listener>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    override fun getItemViewType(position: Int) = differ.currentList[position].getViewType()

    override fun onBindViewHolder(holder: GenericViewHolder<Listener>, position: Int) =
        holder.bind(differ.currentList[position], listener, onViewHolderClicked)

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<Model>, animated: Boolean = true, callback: (() -> Unit)? = null) {
        differ.submitList(list) {
            if (!animated) notifyDataSetChanged()
            callback?.invoke()
        }
    }

    open class GenericViewHolder<Listener>(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        open fun bind(
            model: Model?,
            listener: Listener?,
            onViewHolderClicked: ViewHolderClickCallback? = null
        ) {
            model?.let {
                binding.setVariable(BR.model, it)
            }

            listener?.let {
                binding.setVariable(BR.listener, it)
            }

            onViewHolderClicked?.let { callback ->
                itemView.setOnClickListener { callback.invoke(model, this) }
            }

            binding.executePendingBindings()
        }

        open fun onViewRecycled() {}
    }
}
