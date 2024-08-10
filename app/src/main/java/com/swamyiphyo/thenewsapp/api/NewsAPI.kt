package com.swamyiphyo.thenewsapp.api

import com.swamyiphyo.thenewsapp.models.NewsResponse
import com.swamyiphyo.thenewsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 'suspend' functions are typically used to perform tasks that might take time,
 * such as (network requests or database operations), in an asynchronous,
 * non-blocking manner.
 * The suspend keyword allows the function to be paused (suspended) without blocking the thread it's running on.
 * The function can then resume execution later.
 * 'suspend' functions can only be called from another suspend function or within a coroutine.
 * So, here we will be going to use suspend functions
 */
interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getHeadLines(
        @Query("country") countryCode : String = "mm",
        @Query("page") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ) : Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q") searchQuery : String,
        @Query("page") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ) : Response<NewsResponse>
}