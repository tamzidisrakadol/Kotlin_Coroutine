package com.example.kotlincoroutine.Api

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import retrofit2.Response

fun <T> apiResult(call:suspend ()-> Response<T>):Flow<Pair<Int, ApiResponse<T?>>> = flow {
    emit(0 to ApiResponse.Loading)
    try {
       // val c = withContext(Dispatchers.IO) { call() }
        val c =  call()
        c.let {
            if (c.isSuccessful){
                emit(c.code() to ApiResponse.Success(it.body()))
            }else{
                c.errorBody()?.let { errorMsg->
                    errorMsg.close()
                    emit(c.code() to ApiResponse.Failure(errorMsg.toString()))
                }
            }
        }
    }catch (e:Exception){
        e.printStackTrace()
        emit(0 to ApiResponse.Failure(e.message.toString()))
    }
}.flowOn(Dispatchers.IO)