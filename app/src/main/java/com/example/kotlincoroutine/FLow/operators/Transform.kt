package com.example.kotlincoroutine.FLow.operators

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import java.lang.System.currentTimeMillis

fun main() {
    combineExample()
}


private fun combineExample(){
    /* perform a computation that depends on the most recent values of the corresponding
    flows and to recompute it whenever any of the upstream flows emit a value.
     The corresponding family of operators is called combine
    *Independent Emissions: The flows being combined can emit values independently of each other.
    * */



    val nums = (1..3).asFlow().onEach { delay(100) }
    val strs = flowOf("one", "two", "three").onEach { delay(200) }
    val startTime = System.currentTimeMillis()
    runBlocking(){
        nums.combine(strs){int, strings->
            "$int -> $strings"
        }.collect{
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
    }
}


private fun zipExample(){
    /*  flows have a zip operator that combines the corresponding values of two flows
    *   data print out sequentially
    *   wait for the first one to printout 2nd
    */


    val nums = (1..3).asFlow().onEach { delay(1000) }
    val strs = flowOf("one", "two", "three")
    runBlocking(){
        nums.zip(strs){int, strings->
            "$int -> $strings"
        }.collect{
            println(it)
        }
    }

}


private fun concatExample() {
    /* there will be 2 flows converting into 1. But the twist is if there is any delay it will complete the code sequentially
    * They wait for the inner flow to complete before starting to collect the next*/


    runBlocking() {
        val startTime = currentTimeMillis() // remember the start time
        (1..3).asFlow().onEach {
            delay(100)
        }
            .flatMapConcat {
                requestFlow1(it)
            }
            .collect { value ->
                println("$value at ${currentTimeMillis() - startTime} ms from start")
            }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun mergeExample() {

    /* this operation is to concurrently collect all the
    incoming flows and merge their values into a single flow
    so that values are emitted as soon as possible*/

    runBlocking() {
        val startTime = currentTimeMillis() // remember the start time
        (1..3).asFlow().onEach {
            delay(100)
        }.flatMapMerge {
            requestFlow1(it)
        }.collect { value ->
            println("$value at ${currentTimeMillis() - startTime} ms from start")
        }

    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun mapLatestExample() {
    /*  there is the corresponding "Latest" flattening mode where the collection of the previous flow is cancelled as soon as new flow is emitted. Data will be lost */


    runBlocking() {
        val startTime = currentTimeMillis() // remember the start time
        (1..3).asFlow().onEach { delay(100) }
            .flatMapLatest {
                requestFlow1(it)
            }
            .collect { value ->
                println("$value at ${currentTimeMillis() - startTime} ms from start")
            }
    }
}

private fun requestFlow1(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(1500)
    emit("$i: Second")
}

private fun transformExample(){
    /* Using the transform operator, we can emit arbitrary values an arbitrary number of times.
    * code work as sequentially
    * wait for the first one to complete before starting to collect the next
    */


    runBlocking(Dispatchers.IO){
        (1..3).asFlow()
            .transform {intValue->
                emit("$intValue is emitting")
                emit(performRequest(intValue))
            }.collect{response->
                println(response)
            }
    }
}


suspend fun performRequest(request:Int):String{
    delay(2000)
    return "response no. $request"
}










