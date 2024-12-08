package com.herbamate.herbamate.view.pages.result

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbamate.herbamate.R
import com.herbamate.herbamate.model.Herb
import com.herbamate.herbamate.view.pages.detail.DetailActivity

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private var _herbList : List<Herb>? = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setHerbList (value : List<Herb>?){
        _herbList = value
        notifyDataSetChanged()
    }


    inner class ResultViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageHerb : ImageView = itemView.findViewById(R.id.card_result_image)
        val name : TextView = itemView.findViewById(R.id.card_result_name)
        val latinName : TextView = itemView.findViewById(R.id.card_result_latin_name)
        val description : TextView = itemView.findViewById(R.id.card_result_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_result_herbs, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int = _herbList?.size ?: 0

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        holder.name.text = _herbList?.get(position)?.name
        holder.latinName.text = _herbList?.get(position)?.latinName
        holder.description.text = _herbList?.get(position)?.description

        Glide.with(holder.itemView.context)
            .load(_herbList?.get(position)?.imageLink ?: "https://placehold.co/300/png")
            .error(R.drawable.ic_place_holder)
            .into(holder.imageHerb)

        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_ID, _herbList?.get(position)?.id)
            holder.itemView.context.startActivity(moveIntent)
        }
    }
}
