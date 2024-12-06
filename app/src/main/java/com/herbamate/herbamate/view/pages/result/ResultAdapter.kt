package com.herbamate.herbamate.view.pages.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herbamate.herbamate.databinding.ItemHerbalBinding

//class ResultAdapter(private val herbalList: List<>) :
//    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>(){
//
//    class ResultViewHolder(val binding: ItemHerbalBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
//        val binding = ItemHerbalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ResultViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
//        val herbal = herbalList[position]
//        holder.binding.apply {
//            itemName.text = herbal
//            itemLatin.text = herbal
//            itemDescription.text = herbal
//            Glide.with(root.context)
//                .load(herbal)
//                .into(itemImage)
//        }
//    }
//
//    override fun getItemCount() = herbalList.size
//}