package com.example.kotlincoroutine.Repo

import com.example.kotlincoroutine.Api.ApiInterface
import com.example.kotlincoroutine.Api.ApiResponse
import com.example.kotlincoroutine.Api.ProductApiInterface
import com.example.kotlincoroutine.Api.apiResult
import com.example.kotlincoroutine.Modal.ProductModal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductRepo {
    fun showAllProduct(): Flow<Pair<Int, ApiResponse<ProductModal?>>>
}


class ProductRepoImpl @Inject constructor(val productApiInterface: ProductApiInterface) : ProductRepo {
    override fun showAllProduct() = apiResult {
       productApiInterface.getAllProducts()
    }

}