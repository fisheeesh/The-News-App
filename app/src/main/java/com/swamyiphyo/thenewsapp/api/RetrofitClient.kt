package com.swamyiphyo.thenewsapp.api

import com.swamyiphyo.thenewsapp.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * It's a simpler implementation that just sets up Retrofit with a base URL and a Gson converter.
 * This separation can make it easier to manage and understand, especially in larger projects.
 * If you don’t need detailed logging of HTTP requests and responses,
 * this simpler implementation might be more appropriate.
 */
object RetrofitClient {
    val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
object ApiClient{
    val apiService : NewsAPI by lazy {
        RetrofitClient.retrofit.create(NewsAPI::class.java)
    }
}