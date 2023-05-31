package com.saitejajanjirala.news_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.news_app.data.repo.NewsRepository
import com.saitejajanjirala.news_app.models.Article
import com.saitejajanjirala.news_app.models.HeadLine
import com.saitejajanjirala.news_app.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _headLines = MutableStateFlow<Result<ArrayList<Article>>>(Result.Empty())

    val headLines : StateFlow<Result<ArrayList<Article>>> = _headLines


    private var  category = ""

    fun setCategory(category: String){
        this.category = category
    }
    fun fetch(){
        viewModelScope.launch {
            try {
                _headLines.value = (Result.Loading())
                _headLines.value = repository.getNews(category)
            } catch (e: Exception) {
                _headLines.value = (Result.Error(e.message))
            }
        }
    }

    fun setToRetry() {
        _headLines.value = Result.Error("")
    }


}