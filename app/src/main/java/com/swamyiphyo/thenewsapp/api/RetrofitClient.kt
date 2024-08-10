package com.swamyiphyo.thenewsapp.api

import com.swamyiphyo.thenewsapp.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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