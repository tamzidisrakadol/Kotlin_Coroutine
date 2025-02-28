package com.example.kotlincoroutine.AllClass


/*
* Why Use Value Classes?
Type Safety – Prevents mixing up different values of the same type.
Performance Boost – No extra object creation; it’s inlined.
Better Readability – Gives meaningful names to values.
* */

@JvmInline value class UserId(val id:Int):CheckUser{
    override fun checkUserIdIsValid(id: Int): Boolean {
        if (id==0){
            return false
        }else{
            return true
        }
    }

}
@JvmInline value class ProductId(val id:Int)


data class Customer(
    val userId: UserId,
    val productId: ProductId? = null,
    val totalCost : Int
){
    fun printDataFunc(){
        println("info : user id is ${userId.id}, product id is ${productId?.id} , and total cost is  $totalCost")
        println("check user is valid  : ${userId.checkUserIdIsValid(userId.id)}")

    }
}

interface CheckUser{
    fun checkUserIdIsValid(id:Int):Boolean
}


fun main() {
    val customer = Customer(userId = UserId(12), productId = ProductId(456), totalCost = 100)
    customer.printDataFunc()
}