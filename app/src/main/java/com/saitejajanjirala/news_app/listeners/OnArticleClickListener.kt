package com.saitejajanjirala.news_app.listeners

import com.saitejajanjirala.news_app.models.Article

interface OnArticleClickListener{
    fun onClicked(article : Article)
}