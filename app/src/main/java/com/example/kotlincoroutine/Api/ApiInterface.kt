package com.example.kotlincoroutine.Api

import com.example.kotlincoroutine.Modal.Product
import com.example.kotlincoroutine.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/comments")
    suspend fun getAllComments():Response<List<User>>

    @GET("/products")
    suspend fun getAllProducts(): Response<List<Product>>

}