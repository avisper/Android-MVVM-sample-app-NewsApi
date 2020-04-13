package com.avisper.mvvm.sampleapp.newsapi.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisper.mvvm.sampleapp.newsapi.repository.IRepository
import com.avisper.mvvm.sampleapp.newsapi.repository.NewsRepo
 import com.avisper.mvvm.sampleapp.newsapi.ui.news.NewsViewModel


class ViewModelFactory(
    private val repository: IRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) ->
                return NewsViewModel(
                    repo = repository as NewsRepo
                ) as T

            else -> throw RuntimeException("No ViewModel class found for ${modelClass.canonicalName}")
        }
    }
}