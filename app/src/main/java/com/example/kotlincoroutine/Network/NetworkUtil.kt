package com.example.kotlincoroutine.Network

import CheckNetworkConnectivity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {
    lateinit var connectivity: CheckNetworkConnectivity

    fun init(application: Application) {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivity = CheckNetworkConnectivity(connectivityManager)
    }
}