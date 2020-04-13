package com.avisper.mvvm.sampleapp.newsapi.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.avisper.mvvm.sampleapp.newsapi.db.util.DBConstants.ARTICLES_TABLE_NAME

@Dao
abstract class ArticlesDAO {

    @Query("SELECT * FROM $ARTICLES_TABLE_NAME WHERE category LIKE :categoryTitle ORDER BY publishedAt DESC")
    abstract fun getArticles(categoryTitle: String): List<ArticleEntity>

    @Query("SELECT * FROM $ARTICLES_TABLE_NAME ORDER BY publishedAt DESC")
    abstract fun getArticles(): LiveData<List<ArticleEntity>>

    @Insert(onConflict = REPLACE)
    protected abstract fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM $ARTICLES_TABLE_NAME")
    protected abstract fun deleteAllData()

    @Query("DELETE FROM $ARTICLES_TABLE_NAME WHERE category LIKE :categoryTitle")
    protected abstract fun deleteDataByCategory(categoryTitle: String)

    @Transaction
    open fun insert(articles: List<ArticleEntity>) {
        deleteAllData()
        insertArticles(articles)
    }

    @Transaction
    open fun insert(category: String, articles: List<ArticleEntity>) {
        deleteDataByCategory(category)
        insertArticles(articles)
    }
}