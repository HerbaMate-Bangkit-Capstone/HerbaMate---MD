package com.herbamate.herbamate.view.pages.recommendation_result

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbamate.herbamate.R
import com.herbamate.herbamate.model.HerbRecommendation

class RecommendationResultAdapter : RecyclerView.Adapter<RecommendationResultAdapter.RecommendationResultViewHolder>() {

    private var _herbList : List<HerbRecommendation>? = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setHerbList (value : List<HerbRecommendation>?){
        _herbList = value
        notifyDataSetChanged()
    }


    inner class RecommendationResultViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageHerb : ImageView = itemView.findViewById(R.id.item_image)
        val name : TextView = itemView.findViewById(R.id.item_name)
        val latinName : TextView = itemView.findViewById(R.id.item_latin)
        val usageMethod : TextView = itemView.findViewById(R.id.item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_herbal, parent, false)
        return RecommendationResultViewHolder(view)
    }

    override fun getItemCount(): Int = _herbList?.size ?: 0

    override fun onBindViewHolder(holder: RecommendationResultViewHolder, position: Int) {

        holder.name.text = _herbList?.get(position)?.herbs
        holder.latinName.text = _herbList?.get(position)?.latinName
        holder.usageMethod.text = _herbList?.get(position)?.usageMethod

        Glide.with(holder.itemView.context)
            .load(_herbList?.get(position)?.imageLink ?: "https://placehold.co/300/png")
            .error(R.drawable.ic_place_holder)
            .into(holder.imageHerb)


    }
}