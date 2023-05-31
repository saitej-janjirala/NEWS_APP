package com.saitejajanjirala.news_app.listeners

interface OnCategoryClickListener{
    fun onCategoryClicked(category: Pair<String,Boolean>,position : Int)
}