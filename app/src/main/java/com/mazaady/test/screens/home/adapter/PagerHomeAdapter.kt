package com.mazaady.test.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.test.databinding.ItemUpcomingLayoutBinding

class PagerHomeAdapter(private val items: List<Int>) : RecyclerView.Adapter<PagerHomeAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemUpcomingLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Int) {
            binding.imgBackGround.setImageResource(id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemUpcomingLayoutBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}