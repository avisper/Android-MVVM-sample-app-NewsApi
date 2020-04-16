package com.avisper.mvvm.sampleapp.newsapi.di.module

import com.avisper.mvvm.sampleapp.newsapi.BuildConfig
import com.avisper.mvvm.sampleapp.newsapi.network.interceptor.ApiKeyInterceptor
import com.avisper.mvvm.sampleapp.newsapi.network.service.NewsApiService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkingModule = module {

    single<Retrofit> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val apiKeyInterceptor = ApiKeyInterceptor()

        val gson = get<Gson>()

        val client = OkHttpClient.Builder() //
            .addInterceptor(loggingInterceptor) //
            .addInterceptor(apiKeyInterceptor) //
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return@single Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    single<Gson> {
        Gson()
    }

    factory<NewsApiService> {
        val retrofit = get<Retrofit>()
        return@factory retrofit.create(NewsApiService::class.java)
    }
}
