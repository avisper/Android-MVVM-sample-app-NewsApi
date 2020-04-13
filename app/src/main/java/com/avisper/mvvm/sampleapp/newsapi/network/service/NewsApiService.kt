package com.avisper.mvvm.sampleapp.newsapi.network.service

import com.avisper.mvvm.sampleapp.newsapi.network.response.ResponseArticles
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {

    @GET("/v2/top-headlines?country=il")  // country is constant for now
    suspend fun getHeadlinesByCategory(
        @Query("category") category: String
    ): ResponseArticles?

}