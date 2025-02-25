package com.example.kotlincoroutine.AllClass

sealed class UiState<out T> {
    data class Success<T>(val data : T): UiState<T>()
    data class Error(val message: String, val errorCode: Int? = null): UiState<Nothing>()

}

/*
* More flexible from Enum class because for different data class
* cannot inherit
*  */
