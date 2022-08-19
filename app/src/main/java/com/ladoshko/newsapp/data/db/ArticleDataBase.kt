package com.ladoshko.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ladoshko.newsapp.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = true)
abstract class ArticleDataBase : RoomDatabase(){

    abstract fun getArticlesDao() : ArticleDao

}