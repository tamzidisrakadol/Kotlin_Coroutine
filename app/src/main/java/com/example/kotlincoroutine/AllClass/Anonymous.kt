package com.example.kotlincoroutine.AllClass

interface Receiver{
     fun onReceive(data: String){}
     fun onLost(error: Exception){}
}


open class Animal{

    open fun makeSound(){

    }
}


fun main() {

    val dog = object : Animal(){
        override fun makeSound() {
            super.makeSound()
        }
    }



    val anonymousClass = object : Receiver {
        override fun onReceive(data: String) {
            TODO("Not yet implemented")
        }

        override fun onLost(error: Exception) {
            TODO("Not yet implemented")
        }
    }

    anonymousClass.onReceive("")
}