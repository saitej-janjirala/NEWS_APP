package com.saitejajanjirala.news_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saitejajanjirala.news_app.models.Article

@Database(entities = [Article::class], exportSchema = false, version = 1)
@TypeConverters(
    SourceTypeConverter::class
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun getArticleDao() : ArticleDao

    companion object{
        fun getInstance(context : Context): DatabaseService{
            return Room.databaseBuilder(context.applicationContext,DatabaseService::class.java,"NEWS_APP").build()
        }
    }
}