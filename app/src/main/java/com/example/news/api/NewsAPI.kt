package com.example.news.api

import com.example.news.model.News
import com.example.news.model.Sources
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("sources")
    suspend fun getSources(): Sources

    @GET("everything")
    suspend fun getNews(@Query("sources") sourceId: String): News
}