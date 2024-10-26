package com.mazaady.test.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.test.databinding.ItemChatLayoutBinding


class HomeChatAdapter :
    ListAdapter<Int, HomeChatAdapter.HomeAdsViewHolder>(
        HomeAdsDiffUtil()
    ) {
    class HomeAdsViewHolder(private val itemChatLayoutBinding: ItemChatLayoutBinding) :
        RecyclerView.ViewHolder(itemChatLayoutBinding.root) {
        fun bind(id: Int) {
            itemChatLayoutBinding.imgItemChat.setImageResource(id)
        }
    }

    class HomeAdsDiffUtil : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(
            oldItem: Int,
            newItem: Int
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Int,
            newItem: Int
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeAdsViewHolder(ItemChatLayoutBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HomeAdsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
