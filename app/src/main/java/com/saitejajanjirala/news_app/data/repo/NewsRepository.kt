package com.saitejajanjirala.news_app.data.repo
import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.models.Result


interface NewsRepository {
    abstract suspend fun getNews() : Result<HeadLine>
}