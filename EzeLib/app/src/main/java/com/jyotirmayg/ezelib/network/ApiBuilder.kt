package com.jyotirmayg.ezelib.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiBuilder {
    private const val BASE_URL = "https://demo.ezetap.com//"
    private val retrofit: Retrofit? = null
    private val httpClient: OkHttpClient? = null

    private fun getAuthRetrofit() = retrofit ?: Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder()
            .addInterceptor(getHttpLogInterceptor())
            .connectTimeout(5, TimeUnit.SECONDS)
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build() //Doesn't require the adapter


    private fun getHttpLogInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val API_SERVICE: ApiService = getAuthRetrofit().create(ApiService::class.java)
}