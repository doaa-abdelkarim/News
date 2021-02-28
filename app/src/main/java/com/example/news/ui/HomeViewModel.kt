package com.example.news.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.Article
import com.example.news.model.Source
import com.example.news.repository.articles.ArticlesRepository
import com.example.news.repository.sources.SourcesRepository
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(val sourcesRepository: SourcesRepository,
                                                 val articlesRepository: ArticlesRepository)
    : ViewModel() {

    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val articlesLiveData = MutableLiveData<List<Article?>?>()
    val messageLiveData = MutableLiveData<String>()
    val isResponseSuccessful =  MutableLiveData<Boolean>()

    fun getSources() {
        viewModelScope.launch {
            try {
                val result = sourcesRepository.getSources()
                sourcesLiveData.value = result
            } catch (e: Exception) {
                isResponseSuccessful.value = false
                messageLiveData.value = e.localizedMessage
            }
        }
    }

    fun getArticles(sourceId: String?) {
        viewModelScope.launch {

            try {
                val result = articlesRepository.getArticles(sourceId!!)
                isResponseSuccessful.value = true
                articlesLiveData.value = result

            } catch (e: Exception) {
                isResponseSuccessful.value = false
                messageLiveData.value = e.localizedMessage
            }
        }
    }
}