package com.saitejajanjirala.news_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saitejajanjirala.news_app.R
import com.saitejajanjirala.news_app.databinding.HeadlineItemBinding
import com.saitejajanjirala.news_app.listeners.OnArticleClickListener
import com.saitejajanjirala.news_app.models.Article

class HeadLinesAdapter(private var list : List<Article>, val listener : OnArticleClickListener) : RecyclerView.Adapter<HeadLinesAdapter.HeadLinesViewHolder>() {

    inner class HeadLinesViewHolder(private val binding: HeadlineItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindData( article: Article){
            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .error(R.drawable.baseline_error_24)
                .into(binding.headlineImage)
            binding.headlineTitle.text = article.title
            binding.headlinesDescription.text = article.description
        }

        init {
            binding.root.setOnClickListener {
                val position = this.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onClicked(article = list[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLinesViewHolder {
        val binding:HeadlineItemBinding  =
            HeadlineItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return HeadLinesViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HeadLinesViewHolder, position: Int) {
        if(position != RecyclerView.NO_POSITION){
            val article = list[position]
            holder.bindData(article)
        }
    }

    fun setData(articles : List<Article>){
        this.list = articles
    }
}

