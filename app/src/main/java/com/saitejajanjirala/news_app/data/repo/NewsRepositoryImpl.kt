package com.saitejajanjirala.news_app.data.repo

import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.models.Result
import com.saitejajanjirala.news_app.data.network.ApiService
import com.saitejajanjirala.news_app.utils.Keys
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api : ApiService) : NewsRepository {
    override suspend fun getNews(): Result<HeadLine> {
        val res = api.getHeadLines()
        return if(res != null) {
            if(res.status.equals(Keys.STATUS_OK)) {
                (Result.Success(res))
            } else{
                (Result.Error(res.message))
            }
        } else{
            (Result.Error("Unexpected error occurred please try again"))
        }
    }


}