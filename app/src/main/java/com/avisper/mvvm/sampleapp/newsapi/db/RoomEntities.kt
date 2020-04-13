package com.avisper.mvvm.sampleapp.newsapi.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avisper.mvvm.sampleapp.newsapi.db.util.DBConstants
import com.avisper.mvvm.sampleapp.newsapi.network.response.ArticleModel
import java.util.Date

@Entity(tableName = DBConstants.ARTICLES_TABLE_NAME)
data class ArticleEntity(
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @PrimaryKey // just for now, url is PrimaryKey instead of id field
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: Date? = null
) {
    fun toArticleModel() = ArticleModel(
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}