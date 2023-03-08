package com.saitejajanjirala.news_app.data.network

import android.util.Log
import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.utils.Keys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET(Keys.HEAD_LINES)
    suspend fun getHeadLines() : HeadLine?

    companion object{
        fun getInstance(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().apply {
                addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val originalRequest = chain.request()
                        val originalUrl = originalRequest.url
                        val newRequest=originalRequest.newBuilder().apply {
                            url(originalUrl.newBuilder()
                                .addQueryParameter("apiKey",Keys.API_KEY)
                                .addQueryParameter("country","us").build())

                        }.build()
                        return chain.proceed(newRequest)
                    }
                })
            }
            httpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(Keys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ApiService::class.java)
        }

    }
}