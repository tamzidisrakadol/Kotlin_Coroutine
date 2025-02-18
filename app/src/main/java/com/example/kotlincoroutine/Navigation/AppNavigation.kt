package com.example.kotlincoroutine.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlincoroutine.ProductScreen
import com.example.kotlincoroutine.SearchScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productScreen"){
        composable(route = "productScreen"){
            ProductScreen(navController = navController)
        }
        composable(route = "searchScreen"){
            SearchScreen()
        }
    }
}