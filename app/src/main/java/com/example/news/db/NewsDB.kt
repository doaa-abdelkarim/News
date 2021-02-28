package com.example.news.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.news.model.Article
import com.example.news.model.Source

@Database(entities = [Source::class, Article::class], version = 1)
abstract class NewsDB : RoomDatabase() {

    abstract fun sourceDao(): SourceDao
    abstract fun articleDao(): ArticleDao

    companion object {

        private var instance: NewsDB? = null

        fun getInstance(context: Context): NewsDB {
            if (instance == null) {
                instance =
                    Room
                    .databaseBuilder(
                        context.applicationContext,
                        NewsDB::class.java,
                        "news_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}