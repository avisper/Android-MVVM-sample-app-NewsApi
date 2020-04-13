package com.avisper.mvvm.sampleapp.newsapi.network.response


import com.avisper.mvvm.sampleapp.newsapi.db.ArticleEntity
import com.google.gson.annotations.SerializedName
import java.util.*

data class ResponseArticles(
    @SerializedName("articles")
    val articles: List<ArticleModel>?
)

data class ArticleModel(
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String? = null,
    @SerializedName("publishedAt")
    var publishedAt: Date? = null
) {

    fun toRoomEntity(category: String) =
        ArticleEntity(
            category = category,
            title = title,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt
        )


    override fun toString(): String {
        return "ArticleModel(title=$title, url=$url, urlToImage=$urlToImage, publishedAt=$publishedAt)"
    }


}

