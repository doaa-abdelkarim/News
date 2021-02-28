package com.example.news.repository.articles

import com.example.news.api.NewsAPI
import com.example.news.db.NewsDB
import com.example.news.model.Article
import com.example.news.util.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesRepositoryImp (
    val onlineArticles: OnlineArticles,
    val offlineArticles: OfflineArticles,
    val networkHandler: NetworkHandler
) : ArticlesRepository {
    override suspend fun getArticles(sourceId: String): List<Article?> {
        if (networkHandler.isOnline()) {
            val articles = onlineArticles.getArticles(sourceId)
            offlineArticles.cacheArticles(articles)
            return articles
        }
        return offlineArticles.getArticles(sourceId)
    }
}

class OnlineArticlesImp(val newsAPI: NewsAPI) :
    OnlineArticles {
    override suspend fun getArticles(sourceId: String): List<Article?> {
        val newsResponse = newsAPI.getNews(sourceId)
        return newsResponse.articles ?: listOf<Article>()
    }
}

class OfflineArticlesImp(val newsDB: NewsDB):
    OfflineArticles {
    override suspend fun cacheArticles(articles: List<Article?>) {

        for (i in 0..articles.lastIndex) {
            articles[i]?.sourceId = articles[i]?.source?.id
        }

        withContext(Dispatchers.IO) {
            newsDB.articleDao().cacheArticles(articles)
        }
    }

    override suspend fun getArticles(sourceId: String) = withContext(Dispatchers.IO) {
        newsDB.articleDao().getArticlesBySourceId(sourceId)
    }
}