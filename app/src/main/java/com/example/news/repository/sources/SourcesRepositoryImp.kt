package com.example.news.repository.sources

import com.example.news.api.NewsAPI
import com.example.news.db.NewsDB
import com.example.news.model.Source
import com.example.news.util.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SourcesRepositoryImp (
    val onlineSources: OnlineSources,
    val offlineSources: OfflineSources,
    val networkHandler: NetworkHandler
) : SourcesRepository{
    override suspend fun getSources(): List<Source?> {
        if (networkHandler.isOnline()) {
            val sources = onlineSources.getSources()
            offlineSources.cacheSources(sources)
            return sources
        }
        return offlineSources.getSources()
    }
}

class OnlineSourcesImp(val newsAPI: NewsAPI) : OnlineSources {
    override suspend fun getSources(): List<Source?> {
        val sourcesResponse = newsAPI.getSources()
        return sourcesResponse.sources ?: listOf<Source>()
    }
}

class OfflineSourcesImp(val newsDB: NewsDB): OfflineSources{
    override suspend fun cacheSources(sources: List<Source?>) {
        withContext(Dispatchers.IO) {
            newsDB.sourceDao().cacheSources(sources)
        }
    }

    override suspend fun getSources() = withContext(Dispatchers.IO) {
        newsDB.sourceDao().getAllSources()
    }
}