package com.example.news.repository.articles

import com.example.news.model.Article

interface ArticlesRepository {
    suspend fun getArticles(sourceId: String): List<Article?>
}

interface OnlineArticles :
    ArticlesRepository

interface OfflineArticles :
    ArticlesRepository {
    suspend fun cacheArticles(articles: List<Article?>)
}