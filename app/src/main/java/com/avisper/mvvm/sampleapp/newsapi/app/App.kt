package com.avisper.mvvm.sampleapp.newsapi.app

import android.app.Application
import com.facebook.stetho.Stetho

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDBViewer()
    }

    private fun initDBViewer() {
        Stetho.initializeWithDefaults(this)
    }
}