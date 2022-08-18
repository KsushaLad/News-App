package com.ladoshko.newsapp.data.api

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(countryCode = countryCode, page = pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)

}