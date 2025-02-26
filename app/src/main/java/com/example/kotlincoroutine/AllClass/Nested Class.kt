package com.example.kotlincoroutine.AllClass

class OuterClass{
    private val outerProperty = "I am outer Property"

    fun printOuterProperty(){
        println("I am outer property")
    }
    class NestedClass{

        //cannot access outer property
        fun printNestedProperty(){
            println("I am nested property")
        }
    }
}

fun main() {
    val outerVal = OuterClass()
    outerVal.printOuterProperty()

    val innerVal= OuterClass.NestedClass() // create object to access nested class
    innerVal.printNestedProperty()
}