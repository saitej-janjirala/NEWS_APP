package com.saitejajanjirala.news_app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saitejajanjirala.news_app.data.db.DatabaseService
import com.saitejajanjirala.news_app.data.network.ApiService
import com.saitejajanjirala.news_app.utils.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getApiService() = ApiService.getInstance()


    @Provides
    fun getDatabase(@ApplicationContext context: Context) : DatabaseService{
        return DatabaseService.getInstance(context)
    }
}