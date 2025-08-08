package com.android.plottest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.plottest.databinding.ItemPlotBinding
import com.android.plottest.repository.PlotModel


class PlotAdapter(
    val itemPressed: (item: PlotModel) -> Unit
) :
    ListAdapter<PlotModel, PlotAdapter.ViewHolder>(DiffUtilCallBacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ItemPlotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = holder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }

    inner class ViewHolder(val binding: ItemPlotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myItem: PlotModel, position: Int) {

            binding.plotName.text = myItem.name

            binding.root.setOnClickListener {
                itemPressed(myItem)
            }

        }

    }

    class DiffUtilCallBacks() : DiffUtil.ItemCallback<PlotModel>() {
        override fun areItemsTheSame(oldItem: PlotModel, newItem: PlotModel): Boolean =
            newItem == oldItem

        override fun areContentsTheSame(oldItem: PlotModel, newItem: PlotModel): Boolean =
            newItem == oldItem

    }
}