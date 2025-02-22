package com.example.kotlincoroutine.AllClass

/* A data object is a singleton that can be used in places where a data class is expected,
 such as in sealed hierarchies or when you need a meaningful toString(), equals(), and hashCode() implementation.
 */

sealed class NetworkState{
    object Loading: NetworkState()
    data class Success(val data: String): NetworkState()
    data class Error(val error: String): NetworkState()

}
fun checkNetworkState(networkState: NetworkState){
    when(networkState){
        is NetworkState.Error -> {
            println("error occured")
        }
        NetworkState.Loading ->{
            println("loading")
        }
        is NetworkState.Success -> {
            println("success")
        }
    }
}


data object MyDataObject // default to string
object MyObject // no to String


fun main(){
    val networkState = NetworkState.Loading
    checkNetworkState(networkState)

    println(MyDataObject)
    println(MyObject)

}
