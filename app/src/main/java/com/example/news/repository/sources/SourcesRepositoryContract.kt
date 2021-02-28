package com.example.news.repository.sources

import com.example.news.model.Source

interface SourcesRepository {
    suspend fun getSources(): List<Source?>
}

interface OnlineSources : SourcesRepository

interface OfflineSources : SourcesRepository {
    suspend fun cacheSources(sources: List<Source?>)
}