package com.saitejajanjirala.news_app.models


import com.google.gson.annotations.SerializedName

data class HeadLine(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?,
    @SerializedName("message")
    var message :String?
)