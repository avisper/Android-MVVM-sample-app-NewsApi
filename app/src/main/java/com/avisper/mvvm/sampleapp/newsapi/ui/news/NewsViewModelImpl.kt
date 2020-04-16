package com.avisper.mvvm.sampleapp.newsapi.ui.news

import android.util.Log
import androidx.lifecycle.*
import com.avisper.mvvm.sampleapp.newsapi.enums.eCategory
import com.avisper.mvvm.sampleapp.newsapi.network.response.ArticleModel
import com.avisper.mvvm.sampleapp.newsapi.repository.NewsRepo
import com.avisper.mvvm.sampleapp.newsapi.utils.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


interface NewsViewModel {
    val articles: LiveData<List<ArticleModel>>
    val uiState: LiveData<UiState>

    fun loadHeadlines(category: eCategory)
}

class NewsViewModelImpl(private val repo: NewsRepo) : ViewModel(), NewsViewModel {

    companion object {
        val TAG = NewsViewModelImpl::class.java.simpleName
    }

    override val articles = repo.articles
    override val uiState = MutableLiveData<UiState>()


    override fun loadHeadlines(category: eCategory) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, throwable.toString())
            postUiState(
                UiState(
                    showLoader = false,
                    shouldDisplayError = true,
                    errorText = throwable.toString()
                )
            )
        }) {

            val state = UiState(showLoader = true)
            postUiState(state)

            repo.loadHeadlinesByCategory(category)

            state.showLoader = false
            postUiState(state)
        }
    }

    private fun postUiState(state: UiState) {
        uiState.value = state
    }

}