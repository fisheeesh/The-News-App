package com.swamyiphyo.thenewsapp.api

import com.swamyiphyo.thenewsapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The logging interceptor is configured to log the body of HTTP requests and responses,
 * which can be very useful for debugging network issues.
 * This might be easier to implement in smaller projects or where logging is essential.
 * If you need to log network traffic for debugging, this class is better suited for that purpose.
 */
class RetrofitInstance {
    companion object{
        private val retrofit : Retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()


            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: NewsAPI by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}