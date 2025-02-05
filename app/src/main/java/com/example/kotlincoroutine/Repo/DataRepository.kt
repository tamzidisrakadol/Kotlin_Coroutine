package com.example.kotlincoroutine.Repo

import com.example.kotlincoroutine.Api.ApiInterface
import com.example.kotlincoroutine.Api.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    val apiInterface: ApiInterface,
) {
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
//    suspend fun fetchUserData() = withContext(defaultDispatcher) {
//        ApiResult {
//            apiInterface.getAllComments()
//        }
//    }

    suspend fun fetchUserData() =
        ApiResult {
            apiInterface.getAllComments()
        }

}