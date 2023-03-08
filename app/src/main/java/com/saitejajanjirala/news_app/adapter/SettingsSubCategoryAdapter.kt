package com.saitejajanjirala.news_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.news_app.databinding.SubSettingsRecyclerviewItemBinding

class SettingsSubCategoryAdapter(private val list: MutableList<String>) :
    RecyclerView.Adapter<SettingsSubCategoryAdapter.SettingsSubCategoryViewHolder>() {

    inner class SettingsSubCategoryViewHolder(binding: SubSettingsRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingsSubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SubSettingsRecyclerviewItemBinding.inflate(inflater)
        return SettingsSubCategoryViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SettingsSubCategoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}