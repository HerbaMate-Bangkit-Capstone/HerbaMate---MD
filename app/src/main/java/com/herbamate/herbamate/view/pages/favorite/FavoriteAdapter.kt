package com.herbamate.herbamate.view.pages.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbamate.herbamate.data.local.entity.Favorite
import com.herbamate.herbamate.databinding.CardResultHerbsBinding
import com.herbamate.herbamate.databinding.ItemHerbalBinding

class FavoriteAdapter(private val onItemClicked: (Int) -> Unit) :
    ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardResultHerbsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite, onItemClicked)
    }

    class ViewHolder(private val binding: CardResultHerbsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite, onItemClicked: (Int) -> Unit) {

            binding.cardResultName.text = favorite.herbalName
            binding.cardResultLatinName.text = favorite.herbalLatin
            binding.cardResultDescription.text = favorite.herbalDescription

            Glide.with(binding.root.context).load(favorite.herbalImage).into(binding.cardResultImage)

            binding.root.setOnClickListener {
                onItemClicked(favorite.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}