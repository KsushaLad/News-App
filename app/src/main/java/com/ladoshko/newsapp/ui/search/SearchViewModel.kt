package com.ladoshko.newsapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladoshko.newsapp.data.api.NewsRepository
import com.ladoshko.newsapp.models.NewsResponse
import com.ladoshko.newsapp.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel(){

    val searchMutableLiveData: MutableLiveData<Resourse<NewsResponse>> = MutableLiveData()
    var newsSearchPage = 1

    init {
        getSearchNews("")
    }

    fun getSearchNews(query: String) =
        viewModelScope.launch {
            searchMutableLiveData.postValue(Resourse.Loading())
            val response = repository.searchNews(query = query, pageNumber = newsSearchPage)
            if (response.isSuccessful){
                response.body().let {
                    searchMutableLiveData.postValue(Resourse.Success(it))
                }
            } else{
                searchMutableLiveData.postValue(Resourse.Error(message = response.message()))
            }
        }

}