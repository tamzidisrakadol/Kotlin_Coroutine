package com.example.kotlincoroutine.Modal

sealed class ProductUiState<out T> {
    data object Loading : ProductUiState<Nothing>()
    data class Success<T>(val data: T) : ProductUiState<T>()
    data class Error(val message: String, val errorCode: Int? = null) : ProductUiState<Nothing>()
}