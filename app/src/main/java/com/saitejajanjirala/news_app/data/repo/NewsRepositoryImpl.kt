package com.saitejajanjirala.news_app.data.repo

import com.saitejajanjirala.news_app.data.db.ArticleDao
import com.saitejajanjirala.news_app.data.db.DatabaseService
import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.models.Result
import com.saitejajanjirala.news_app.data.network.ApiService
import com.saitejajanjirala.news_app.models.Article
import com.saitejajanjirala.news_app.utils.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api : ApiService,
    private val databaseService: DatabaseService) : NewsRepository {
    override suspend fun getNews(category: String): Result<ArrayList<Article>> {
        return try {
            val res = api.getHeadLines(category)
            return if(res != null) {
                if(res.status.equals(Keys.STATUS_OK)) {
                    insertIntoDatabase(res)
                } else{
                    fetchFromDatabase()
                }
            } else{
                fetchFromDatabase()
            }
        }
        catch (e: Exception){
            fetchFromDatabase()
        }
    }

    override suspend fun fetchFromDatabase(): Result<ArrayList<Article>> {
        return withContext(Dispatchers.IO){
            val dao = databaseService.getArticleDao()
            try{
                val a = dao.getAllArticles()
                return@withContext if(a.isNotEmpty()){
                    Result.Success(ArrayList(a))
                }
                else{
                    Result.Error("")
                }
            }catch (e : Exception){
                return@withContext Result.Error("")
            }
        }
    }

    override suspend fun insertIntoDatabase(res: HeadLine): Result<ArrayList<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                val dao = databaseService.getArticleDao()
                dao.clearAllArticles()
                dao.insertArticles(res.articles as ArrayList<Article>)
                return@withContext Result.Success(ArrayList(dao.getAllArticles()))
            }catch (e : Exception){
                return@withContext (Result.Error(""))
            }

        }
    }


}