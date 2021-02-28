package com.example.news.dependencyinjection

import android.content.Context
import com.example.news.api.NewsAPI
import com.example.news.db.NewsDB
import com.example.news.repository.articles.*
import com.example.news.repository.sources.*
import com.example.news.util.NetworkHandler
import com.example.news.util.NetworkHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@InstallIn(ApplicationComponent::class)
@Module
class RepositoriesModule {

    @Provides
    fun provideSourcesRepository(
        onlineSources: OnlineSources,
        offlineSources: OfflineSources,
        networkHandler: NetworkHandler
    ): SourcesRepository {
        return SourcesRepositoryImp(onlineSources, offlineSources, networkHandler)
    }

    @Provides
    fun provideOnlineSources(newsAPI: NewsAPI): OnlineSources {
        return OnlineSourcesImp(newsAPI)
    }

    @Provides
    fun provideOfflineSources(newsDB: NewsDB): OfflineSources {
        return OfflineSourcesImp(newsDB)
    }

    @Provides
    fun provideArticlesRepository(
        onlineArticles: OnlineArticles,
        offlineArticles: OfflineArticles,
        networkHandler: NetworkHandler
    ): ArticlesRepository {
        return ArticlesRepositoryImp(onlineArticles, offlineArticles, networkHandler)
    }

    @Provides
    fun provideOnlineArticles(newsAPI: NewsAPI): OnlineArticles {
        return OnlineArticlesImp(newsAPI)
    }

    @Provides
    fun provideOfflineArticles(newsDB: NewsDB): OfflineArticles {
        return OfflineArticlesImp(newsDB)
    }

    @Provides
    fun provideNewsDBInstance(@ApplicationContext context: Context) = NewsDB.getInstance(context)

    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandlerImpl(context)
    }
}