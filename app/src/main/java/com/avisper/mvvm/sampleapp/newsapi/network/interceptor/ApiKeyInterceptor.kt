package com.avisper.mvvm.sampleapp.newsapi.network.interceptor

import com.avisper.mvvm.sampleapp.newsapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}