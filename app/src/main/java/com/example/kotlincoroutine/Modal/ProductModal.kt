package com.example.kotlincoroutine.Modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModal(
    val limit: Int=0,
    val products: List<Product> = emptyList(),
    val skip: Int=0,
    val total: Int=0
) : Parcelable