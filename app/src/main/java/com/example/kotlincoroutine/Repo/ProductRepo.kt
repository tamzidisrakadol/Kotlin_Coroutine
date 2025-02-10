package com.example.kotlincoroutine.Repo

import com.example.kotlincoroutine.Api.ApiInterface
import com.example.kotlincoroutine.Api.ApiResponse
import com.example.kotlincoroutine.Api.apiResult
import com.example.kotlincoroutine.Modal.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductRepo {
    fun showAllProduct(): Flow<Pair<Int, ApiResponse<List<Product>?>>>
}


class ProductRepoImpl @Inject constructor(val apiInterface: ApiInterface) : ProductRepo {
    override fun showAllProduct() = apiResult {
        apiInterface.getAllProducts()
    }

}