package com.saitejajanjirala.news_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saitejajanjirala.news_app.R
import com.saitejajanjirala.news_app.databinding.CategoryItemBinding
import com.saitejajanjirala.news_app.databinding.HeadlineItemBinding
import com.saitejajanjirala.news_app.listeners.OnCategoryClickListener
import com.saitejajanjirala.news_app.models.Article

class CategoriesAdapter (private var list :ArrayList<Pair<String,Boolean>>, val listener : OnCategoryClickListener) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindData( category: Pair<String,Boolean>){
            binding.categoryText.text = category.first
            if(category.second){
                binding.categoryLayout.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context,R.color.purple_500)
                )
            }
            else{
                binding.categoryLayout.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context,R.color.purple_200)
                )
            }
        }

        init {
            binding.root.setOnClickListener {
                val position = this.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onCategoryClicked(category = list[position], position = position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: CategoryItemBinding =
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if(position != RecyclerView.NO_POSITION){
            val category = list[position]
            holder.bindData(category)
        }
    }


    fun setData(categories : ArrayList<Pair<String,Boolean>>){
        this.list = categories
    }
}