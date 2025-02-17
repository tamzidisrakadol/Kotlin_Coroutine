package com.example.kotlincoroutine.Modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
sealed class ProductUiState<out T> :Parcelable {
    @Parcelize
    data object Loading : ProductUiState<Nothing>()
    @Parcelize
    data class Success<T>(val data: @RawValue T) : ProductUiState<T>()

    @Parcelize
    data class Error(val message: String, val errorCode: Int? = null) : ProductUiState<Nothing>()
}