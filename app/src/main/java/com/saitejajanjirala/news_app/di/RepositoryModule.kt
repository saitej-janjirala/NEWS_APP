package com.saitejajanjirala.news_app.di

import com.saitejajanjirala.news_app.data.repo.NewsRepository
import com.saitejajanjirala.news_app.data.repo.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
 interface RepositoryModule {

    @Binds
    @Singleton
    fun bindNewsRepository(
        weatherRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}