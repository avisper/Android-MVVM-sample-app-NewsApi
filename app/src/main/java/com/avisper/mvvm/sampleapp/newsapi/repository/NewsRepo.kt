package com.avisper.mvvm.sampleapp.newsapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.avisper.mvvm.sampleapp.newsapi.db.ArticlesDAO
import com.avisper.mvvm.sampleapp.newsapi.enums.eCategory
import com.avisper.mvvm.sampleapp.newsapi.network.response.ArticleModel
import com.avisper.mvvm.sampleapp.newsapi.network.service.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface NewsRepo {
    val articles: LiveData<List<ArticleModel>>

    suspend fun loadHeadlinesByCategory(category: eCategory)
}

class NewsRepoImpl(
    private val newsApiService: NewsApiService,
    private val dao: ArticlesDAO
) : NewsRepo {
    override val articles: LiveData<List<ArticleModel>> =
        Transformations.map(dao.getArticles()) { list ->
            list.map { articleEntity -> articleEntity.toArticleModel() }
        }

    override suspend fun loadHeadlinesByCategory(category: eCategory) {
        withContext(Dispatchers.IO) {
            val categoryTitle = category.title
            val responseArticles = newsApiService.getHeadlinesByCategory(categoryTitle)

            if (responseArticles == null || responseArticles.articles.isNullOrEmpty()) {
                return@withContext
            }
            val articleEntityList =
                responseArticles.articles.map { articleModel ->
                    articleModel.toRoomEntity(categoryTitle)
                }
            if (!articleEntityList.isNullOrEmpty()) {
                dao.insert(articleEntityList)
            } else {
                throw Exception("some error with articleEntityList")
            }
        }
    }

}