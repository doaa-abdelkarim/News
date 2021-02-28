package com.example.news.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.news.model.Article

@Dao
interface ArticleDao {

    @Insert
    fun cacheArticles(articles: List<Article?>)

    @Query("SELECT * FROM article WHERE sourceId = :sourceId")
    fun getArticlesBySourceId(sourceId: String): List<Article>

    @Query("DELETE FROM article")
    fun removeAll()
}