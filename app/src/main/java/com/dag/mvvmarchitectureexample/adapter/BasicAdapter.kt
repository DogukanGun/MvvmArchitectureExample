package com.dag.mvvmarchitectureexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dag.mvvmarchitectureexample.BR


open class BasicAdapter<T> protected constructor(
    private val itemLayout: Int,
    private val itemClickListener: ItemClickListener<T>?,
    private val selectionEnabled: Boolean,
    private val list: List<T>,
) : RecyclerView.Adapter<BasicAdapter<T>.ViewHolder>(){

    private var selectedIndex = RecyclerView.NO_POSITION

    override fun getItemViewType(position: Int): Int {
        return itemLayout
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.onRecycled()
    }

    private fun selectPosition(position: Int) {
        if (selectedIndex == position) return

        if (selectedIndex != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedIndex)
        }

        selectedIndex = position
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedIndex)
        }
    }

    inner class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            if (itemClickListener != null || selectionEnabled) {
                itemView.setOnClickListener { clicked() }
            }
        }

        fun bind(item: T) {
            binding.setVariable(BR.item, item)
            if (selectionEnabled) {
                itemView.isSelected = adapterPosition == selectedIndex
            }
            binding.executePendingBindings()
        }

        fun onRecycled() {

        }

        private fun clicked() {
            if (adapterPosition == RecyclerView.NO_POSITION) return
            if (selectionEnabled) selectPosition(adapterPosition)
            itemClickListener?.onClick(adapterPosition, list[adapterPosition])
        }
    }

    companion object {
        fun <T> createWith(builder: Builder<T>): BasicAdapter<T> {
            return BasicAdapter(
                builder.itemLayoutId,
                builder.itemClickListener,
                builder.itemSelectionEnabled,
                builder.list
            )
        }
    }


}

