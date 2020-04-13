package com.avisper.mvvm.sampleapp.newsapi.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avisper.mvvm.sampleapp.newsapi.db.ArticleEntity
import com.avisper.mvvm.sampleapp.newsapi.db.ArticlesDAO
import com.avisper.mvvm.sampleapp.newsapi.enums.eCategory
import com.avisper.mvvm.sampleapp.newsapi.network.response.ArticleModel
import com.avisper.mvvm.sampleapp.newsapi.network.response.ResponseArticles
import com.avisper.mvvm.sampleapp.newsapi.network.service.NewsApiService
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*


class NewsRepoTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val categoryMock = eCategory.technology


    @Test
    fun test_LoadHeadlines_valid_response() {
        val mockApiList = listOf(
            ArticleModel(
                "MOCK TITLE",
                "www.mockurl.com",
                "www.mockimgurl.com",
                Date(0)
            )
        )

        val apiMock = NewsAPIMock(mockApiList)

        val mockDBList = listOf(
            ArticleEntity(
                categoryMock.title,
                "MOCK TITLE",
                "www.mockurl.com",
                "www.mockimgurl.com",
                Date(0)
            )
        )
        val dbMock = ArticleDAOMock()

        val repository = NewsRepoImpl(
            apiMock,
            dbMock
        )

        runBlocking {
            repository.loadHeadlinesByCategory(categoryMock)
        }

        assertTrue(apiMock.didUse)
        assertTrue(dbMock.didUseSave)
        assertEquals(mockDBList, dbMock.articlesLiveData.value)
    }

    @Test
    fun test_LoadHeadlines_emptyList_response() {

        val apiMock = NewsAPIMock(emptyList())

        val dbMock = ArticleDAOMock()

        val repository = NewsRepoImpl(
            apiMock,
            dbMock
        )

        runBlocking {
            repository.loadHeadlinesByCategory(categoryMock)
        }

        assertTrue(apiMock.didUse)
        assertFalse(dbMock.didUseSave)
    }


    @Test
    fun test_LoadHeadlines_null_response() {

        val apiMock = NewsAPIMock(null)

        val dbMock = ArticleDAOMock()

        val repository = NewsRepoImpl(
            apiMock,
            dbMock
        )

        runBlocking {
            repository.loadHeadlinesByCategory(categoryMock)
        }

        assertTrue(apiMock.didUse)
        assertFalse(dbMock.didUseSave)
    }


    /*
        Mocks
     */

    private class NewsAPIMock(val listToReturn: List<ArticleModel>?) : NewsApiService {
        var didUse: Boolean = false
            private set

        override suspend fun getHeadlinesByCategory(category: String): ResponseArticles? {
            didUse = true
            return ResponseArticles(
                listToReturn
            )
        }

    }

    private class ArticleDAOMock : ArticlesDAO() {
        val articlesLiveData = MutableLiveData<List<ArticleEntity>>()

        var listToReturn: List<ArticleEntity> = emptyList()

        var didUseSave: Boolean = false
            private set

        var didUseGet: Boolean = false
            private set

        override fun getArticles(categoryTitle: String): List<ArticleEntity> {
            didUseGet = true
            return listToReturn
        }

        override fun getArticles(): LiveData<List<ArticleEntity>> {
            didUseGet = true
            return articlesLiveData
        }

        override fun insertArticles(articles: List<ArticleEntity>) {
            didUseSave = true
            listToReturn = articles
            articlesLiveData.value = listToReturn
        }

        override fun deleteAllData() {
        }

        override fun deleteDataByCategory(categoryTitle: String) {
        }
    }
}
