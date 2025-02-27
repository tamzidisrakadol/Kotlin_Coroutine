package com.example.kotlincoroutine.AllClass



/*
In Kotlin, an abstract class is a class that cannot be instantiated directly. It is used as a blueprint for other classes to inherit from.
 Abstract classes can contain both abstract members (methods or properties that do not have an implementation)
 and concrete members (methods or properties with an implementation).
They are useful for defining common behavior and structure that subclasses must adhere to while allowing flexibility in how that behavior is implemented.
* */

//Open by Default
abstract class ExampleOfAbstractClass{

    abstract val abstractProperty: String

    abstract fun abstractMethod() : String


    open fun concreteMethod(){ // add open keyword to allow overriding
        println("Calling from Abstract class and I am a Concrete method")
    }
}


class UseCaseOfAbstractClass : ExampleOfAbstractClass(){
    override val abstractProperty: String
        get() = "over-ride abstract property property"

    override fun abstractMethod(): String {
        return "over-ride abstract method"
    }

    override fun concreteMethod() {
        super.concreteMethod()
        println("Calling from UseCaseOfAbstractClass and I am a Concrete method")
    }
}


fun main() {
    val useCase = UseCaseOfAbstractClass()
    useCase.concreteMethod()
    println(useCase.abstractProperty)
    println(useCase.abstractMethod())


}