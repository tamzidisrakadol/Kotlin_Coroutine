package com.example.kotlincoroutine.AllClass

data class ExampleDataClass(
    val id: Int = 0,
    val name: String? = null,
    val emailAddress: String? = null
){

    fun isEmailAddressValid (emailAddress: String?): Boolean{
        return emailAddress?.contains("@") == true
    }

}


fun main() {

    val userDataClass1 = ExampleDataClass()
    println(userDataClass1) //getting the expected result for to-string

    val userDataClass2 = ExampleDataClass(23,"Adol","adol@gmail.com")
    println(userDataClass2)
    println(userDataClass2.isEmailAddressValid(userDataClass2.emailAddress))

    val userDataClass3 = ExampleDataClass().copy(emailAddress = "adol#outlook.com") //used copy for to update single value
    println(userDataClass3.isEmailAddressValid(userDataClass3.emailAddress))


}