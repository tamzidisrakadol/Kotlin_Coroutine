package com.example.kotlincoroutine.AllClass


fun localFunction(){

    val outerVariable = "I am from outter variable" // this cannot changed in local function..if it get changed then it will throw compile time error

    //this can be changed
    val counter = object {
        var value = 10
    }

    class LocalClass{

        //local class can access outer class variable
        fun printLocalClassFun(){
            println("I am from local class function")
            println("$outerVariable is calling")
        }

        fun incremantCounter(){
            counter.value++
            println("Counter value is ${counter.value}")
        }

    }

    val localClass = LocalClass()
    localClass.printLocalClassFun()
    localClass.incremantCounter()
    localClass.incremantCounter()

    //local class can only be called inside local function.

}


fun main() {
    localFunction()
}