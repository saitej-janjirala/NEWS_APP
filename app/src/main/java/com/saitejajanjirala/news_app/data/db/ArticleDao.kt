package com.saitejajanjirala.news_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saitejajanjirala.news_app.models.Article

@Dao
interface ArticleDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles : ArrayList<Article>) : List<Long>

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()


}