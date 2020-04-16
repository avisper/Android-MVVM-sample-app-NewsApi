package com.avisper.mvvm.sampleapp.newsapi.app

import android.app.Application
import com.avisper.mvvm.sampleapp.newsapi.di.modulesList
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDiKoin()
        initDBViewer()
    }

    private fun initDiKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modulesList)
        }
    }

    private fun initDBViewer() {
        Stetho.initializeWithDefaults(this)
    }
}