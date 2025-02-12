package com.example.kotlincoroutine.FLow.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.lang.System.currentTimeMillis

fun main() {
    mapLatestExample()
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










