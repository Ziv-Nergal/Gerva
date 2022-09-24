package com.ziv_nergal.genericrecyclerviewadapter

import android.util.Log
import android.view.InflateException
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter.GenericViewHolder

typealias ViewHolderClickCallback = (Model?, GenericViewHolder) -> Unit

class GenericRecyclerViewAdapter(
    items: List<Model>,
    private val listener: Any? = null,
    private val viewHolderFactory: ViewHolderFactory? = null,
    private val onViewHolderClicked: ViewHolderClickCallback? = null
) : RecyclerView.Adapter<GenericViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Model>() {

        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean =
            oldItem.areItemsTheSame(newItem)

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean =
            oldItem.areContentsTheSame(newItem)
    })

    init {
        updateData(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): GenericViewHolder {

        // If we get a custom viewHolder from the ViewHolder factory, return it, else, create a basic GenericViewHolder
        viewHolderFactory?.createViewHolder(parent, layoutId)?.let { viewHolder ->
            viewHolder.setListener(listener)
            viewHolder.setClickListener(onViewHolderClicked)
            return viewHolder
        }

        val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        ) ?: throw InflateException(
            "Couldn't inflate binding layout for layout ${
                parent.context.resources.getResourceName(
                    layoutId
                ) ?: "id $layoutId"
            }, make sure the id is for a binding layout."
        )

        return GenericViewHolder(viewDataBinding, listener, onViewHolderClicked)
    }

    override fun onViewRecycled(holder: GenericViewHolder) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    override fun getItemViewType(position: Int) = differ.currentList[position].getLayoutId()

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    fun updateData(list: List<Model>, callback: (() -> Unit)? = null) {
        differ.submitList(list) {
            callback?.invoke()
        }
    }

    open class GenericViewHolder(
        protected open val binding: ViewDataBinding,
        protected var listener: Any? = null,
        private var onViewHolderClicked: ViewHolderClickCallback? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        open fun bind(model: Model?) {
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

        internal fun setClickListener(onViewHolderClicked: ViewHolderClickCallback?) {
            this.onViewHolderClicked = onViewHolderClicked
        }

        internal fun setListener(listener: Any?) {
            this.listener = listener
        }
    }
}
