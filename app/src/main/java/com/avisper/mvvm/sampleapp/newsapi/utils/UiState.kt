package com.avisper.mvvm.sampleapp.newsapi.utils

data class UiState(
    var shouldDisplayError: Boolean = false,
    var errorText: String? = "",
    var showLoader: Boolean = false
)

interface IOnUiStateChanged{
    fun onUiStateChanged(uiState: UiState)
}