package com.avisper.mvvm.sampleapp.newsapi.di.module

import com.avisper.mvvm.sampleapp.newsapi.repository.NewsRepo
import com.avisper.mvvm.sampleapp.newsapi.repository.NewsRepoImpl
import org.koin.dsl.module

val reposModule = module {

    single<NewsRepo> {
        NewsRepoImpl(
            newsApiService = get(),
            dao = get()
        )
    }

}
