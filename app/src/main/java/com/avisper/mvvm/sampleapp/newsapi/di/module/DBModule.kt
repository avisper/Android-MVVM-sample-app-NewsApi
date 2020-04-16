package com.avisper.mvvm.sampleapp.newsapi.di.module

import com.avisper.mvvm.sampleapp.newsapi.db.AppDatabase
import com.avisper.mvvm.sampleapp.newsapi.db.ArticlesDAO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single<AppDatabase> {
        AppDatabase.create(androidContext())
    }

    factory<ArticlesDAO> {
        get<AppDatabase>().articlesDao()
    }
}
