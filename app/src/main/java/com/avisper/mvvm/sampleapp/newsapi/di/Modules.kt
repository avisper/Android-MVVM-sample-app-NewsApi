package com.avisper.mvvm.sampleapp.newsapi.di

import com.avisper.mvvm.sampleapp.newsapi.di.module.dbModule
import com.avisper.mvvm.sampleapp.newsapi.di.module.networkingModule
import com.avisper.mvvm.sampleapp.newsapi.di.module.reposModule
import com.avisper.mvvm.sampleapp.newsapi.di.module.viewModelsModule

val modulesList = listOf(
    networkingModule,
    dbModule,
    reposModule,
    viewModelsModule
)