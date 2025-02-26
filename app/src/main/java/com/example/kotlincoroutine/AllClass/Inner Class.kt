package com.example.kotlincoroutine.AllClass

private class OuterClassExample{

    private val outerProperty = "I am from outter property"
    // can access inner property

    inner class InnerClassExample{
        //can access outer property
        fun printInnerFunc(){
            println("print from inner class but also can access outter properter : $outerProperty")
        }

    }

}


fun main() {
    val outerClassExample = OuterClassExample()
    outerClassExample.InnerClassExample().printInnerFunc()
}