package com.example.kotlincoroutine.Repo

import com.example.kotlincoroutine.Api.ApiInterface
import com.example.kotlincoroutine.Api.apiResult
import javax.inject.Inject

class DataRepository @Inject constructor(
    val apiInterface: ApiInterface,
) {
    fun fetchUserData() = apiResult {
       apiInterface.getAllComments()
    }

}