package com.ladoshko.newsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladoshko.newsapp.data.api.NewsRepository
import com.ladoshko.newsapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel(){

    init {
        getSavedArticles()
    }

    fun getSavedArticles() = viewModelScope.launch (Dispatchers.IO){
        repository.getFavouriteArticles()
    }

    fun addToFavourite(article: Article) = viewModelScope.launch(Dispatchers.IO){
        repository.addToFavourite(article = article)
    }

}