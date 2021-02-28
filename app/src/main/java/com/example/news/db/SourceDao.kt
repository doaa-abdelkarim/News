package com.example.news.db

import androidx.room.*
import com.example.news.model.Source

@Dao
interface SourceDao {

    @Query("SELECT * FROM Source")
    fun getAllSources(): List<Source>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheSources(sources: List<Source?>)
}