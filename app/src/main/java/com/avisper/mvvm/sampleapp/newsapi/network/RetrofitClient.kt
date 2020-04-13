package com.avisper.mvvm.sampleapp.newsapi.network

import java.util.concurrent.Executors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val BASE_URL = "https://newsapi.org/"

    val instance: Retrofit by lazy {
        buildClient()
    }


    private fun buildClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(buildLoggingInterceptor())
            .addInterceptor(buildApiKeyInterceptor())
            .build()
    }

    private fun buildApiKeyInterceptor() = ApiKeyInterceptor()


    private fun buildLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

}
