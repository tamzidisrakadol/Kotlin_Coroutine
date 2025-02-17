package com.example.kotlincoroutine.Api

import com.example.kotlincoroutine.Modal.ProductModal
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiInterface {
    @GET("/products")
    suspend fun getAllProducts(): Response<ProductModal>
}