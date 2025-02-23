package com.example.kotlincoroutine.AllClass

enum class HttpStatusCode(val code:Int, val message: String){

    OK(200,"ok"),
    Error(400,"Error"),
    UnAuthorize(404,"UnAuthorize");   // if we add any function we have to add ";"


    fun getHttpStatusCode(): String {
        return " status code is $code and message is $message"
    }
}


fun main() {
    val response = HttpStatusCode.UnAuthorize
    println(response.getHttpStatusCode())


    HttpStatusCode.entries.forEach { // we can print all the constant variables
        println(it.name) //to print all the constant variable names
    }
}