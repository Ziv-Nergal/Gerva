package com.ziv_nergal.genericrecyclerviewadapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter.GenericViewHolder

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<Listener> {
        val genericViewHolder = viewHolderFactory?.createViewHolder(parent, viewType)
            ?: GenericViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
                )
            )
        genericViewHolder.setListener(listener)
        return genericViewHolder
    }

    override fun onViewRecycled(holder: GenericViewHolder<Listener>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    override fun getItemViewType(position: Int) = differ.currentList[position].getViewType()

    override fun onBindViewHolder(holder: GenericViewHolder<Listener>, position: Int) =
        holder.bind(differ.currentList[position], onViewHolderClicked)

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<Model>, animated: Boolean = true, callback: (() -> Unit)? = null) {
        differ.submitList(list) {
            if (!animated) notifyDataSetChanged()
            callback?.invoke()
        }
    }

    open class GenericViewHolder<Listener>(
        protected open val binding: ViewDataBinding,
        protected var listener: Listener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        open fun bind(
            model: Model?,
            onViewHolderClicked: ViewHolderClickCallback? = null
        ) {
            model?.let {
                binding.setVariable(BR.model, it)
            }

            try {
                listener?.let { binding.setVariable(BR.listener, it) }
            } catch (exception: ClassCastException) {
                exception.localizedMessage?.let { Log.d(this.javaClass.simpleName, it) }
            }

            onViewHolderClicked?.let { callback ->
                itemView.setOnClickListener { callback.invoke(model, this) }
            }

            binding.executePendingBindings()
        }

        open fun onViewRecycled() {
            binding.unbind()
        }

        internal fun setListener(listener: Listener?) {
            this.listener = listener
        }
    }
}
