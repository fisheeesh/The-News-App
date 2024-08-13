package com.swamyiphyo.thenewsapp.repository

import com.swamyiphyo.thenewsapp.api.RetrofitInstance
import com.swamyiphyo.thenewsapp.database.ArticleDatabase
import com.swamyiphyo.thenewsapp.models.Article

/**
 * A place or container where sth is deposited or stored
 * with the help of articleDatabase and retrofitInstance or ApiClient
 */
class NewsRepository(private val db : ArticleDatabase) {
    suspend fun getHeadLines(counterCode : String, pageNumber : Int) =
        RetrofitInstance.api.getHeadLines(counterCode, pageNumber)
//        ApiClient.apiService.getHeadLines(counterCode, pageNumber)

    suspend fun searchNews(searchQuery : String, pageNumber : Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
//        ApiClient.apiService.searchForNews(searchQuery, pageNumber)


    suspend fun upsert(article : Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getFavoriteNews() = db.getArticleDao().getAllArticles()

}