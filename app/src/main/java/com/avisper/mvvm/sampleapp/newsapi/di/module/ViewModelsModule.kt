package com.avisper.mvvm.sampleapp.newsapi.di.module

import com.avisper.mvvm.sampleapp.newsapi.ui.news.NewsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel<NewsViewModelImpl> {
        NewsViewModelImpl(repo = get())
    }

}
