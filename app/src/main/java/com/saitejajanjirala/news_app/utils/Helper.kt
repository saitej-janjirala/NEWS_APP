package com.saitejajanjirala.news_app.utils

object Helper {

    fun getListOfCategories() : ArrayList<Pair<String,Boolean>>{
        val list =  mutableListOf<String>("All","business","entertainment","general","health","science","sports","technology")
        val pairList = ArrayList<Pair<String,Boolean>>()
        for(i in list.indices){
            pairList.add(Pair(list[i], i==0))
        }
        return pairList
    }
}