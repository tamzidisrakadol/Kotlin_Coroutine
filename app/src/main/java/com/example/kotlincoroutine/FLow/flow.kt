package com.example.kotlincoroutine.FLow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/* Flows = flows are cold in nature. Which means it will start emitting data while there is an subscriber

* */
fun main() {
    runBlocking {
        //consumer
        userStream()
            .collect { //terminal operator
                val list = it.map {
                    it*2
                }.filter {
                    it>4
                }
                println(list)
            }


        //consumer
        producerStream()
            .map {  //non-terminal operator
                it * 3
            }
            .filter { //non-terminal operator
                it > 10
            }
            .onStart {//terminal operator
                emit(33)
            }
            .onCompletion {//terminal operator
                emit(22)
            }
            .collect{ //terminal operator
                println(it)
            }

    }

}


//Producer
private fun userStream(): Flow<List<Int>> {
    return flow {
        val list = listOf(1, 2, 3, 4, 5)
        emit(list)
        delay(1000)
    }
}

//Producer
private fun producerStream(): Flow<Int> {
    return flow {
        val list = listOf(6, 7, 8, 9, 10)
        list.forEach {
            emit(it)
            delay(1000)
        }
        delay(2000)
        throw Exception("Error occurred")
    }.catch { //can set catch block to handle exception
        println("Exception is $it")
    }.flowOn(Dispatchers.IO)     // can set coroutine context
}
