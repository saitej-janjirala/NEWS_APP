package com.saitejajanjirala.news_app.data.repo
import com.saitejajanjirala.news_app.models.Article
import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.models.Result


interface NewsRepository {
    suspend fun getNews( category : String = "") : Result<ArrayList<Article>>
    suspend fun insertIntoDatabase(res: HeadLine): Result<ArrayList<Article>>
    suspend fun fetchFromDatabase(): Result<ArrayList<Article>>
}