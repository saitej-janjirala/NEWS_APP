package com.saitejajanjirala.news_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.news_app.databinding.SettingsRecyclerviewItemBinding

class SettingsAdapter (private val list: List<Pair<String,MutableList<String>>>) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    class SettingsViewHolder(val binding: SettingsRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pair : Pair<String,MutableList<String>>){
            binding.subHeading.text = pair.first
            val adapter = SettingsSubCategoryAdapter(pair.second)
            binding.subRecyclerview.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SettingsRecyclerviewItemBinding.inflate(inflater)
        return SettingsViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        if(position != RecyclerView.NO_POSITION){
            holder.bind(list[position])
        }
    }
}