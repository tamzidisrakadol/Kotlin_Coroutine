package com.example.kotlincoroutine.AllClass

/* In Kotlin, an object is a special construct used to define a singleton—a class that has only one instance throughout the application.
The object keyword is used to declare a class and create its single instance at the same time.
This is useful when you need a class that should have exactly one instance, such as for managing shared resources, configuration, or utility functions.
*/

object DatabaseConnection{  //no constructor

    val connectUrl = "jdbc_Connect_url"

    fun connect() = println("Database connected with the $connectUrl")

    fun disconnect()= println("Database disconnected from the $connectUrl")

}

fun main() {
    DatabaseConnection.connect()
    DatabaseConnection.disconnect()


    //everytime the result of the instance will be the same
    val secondDatabaseConnection = DatabaseConnection
    secondDatabaseConnection.connect()


}