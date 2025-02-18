package com.example.kotlincoroutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlincoroutine.ViewModel.NetworkViewModel
import com.example.kotlincoroutine.ViewModel.ProductViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel()
) {

    val filterProduct = productViewModel.filterProducts.collectAsStateWithLifecycle()
    val searchText = productViewModel.searchText.collectAsStateWithLifecycle()
    val checkNetworkConnectivity = networkViewModel.isConnected.collectAsStateWithLifecycle()

    when (checkNetworkConnectivity.value) {
        true -> {
            SearchScreenItem(productViewModel=productViewModel)
        }

        false -> {
            NoNetworkScreen()
        }
    }
}
@Composable
private fun SearchScreenItem(modifier: Modifier = Modifier,productViewModel: ProductViewModel) {
    var searchText = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Spacer(modifier = modifier.height(50.dp))
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = searchText.value,
            onValueChange = {
                searchText.value = it
            },
            label = { Text("Search Product") }
        )

        Spacer(modifier = modifier.height(10.dp))

        Button(
            onClick = {
              productViewModel.updateSearchText(searchText.value)
            }
        ) {
            Text("Search")
        }
        Spacer(modifier = modifier.height(10.dp))
        Text("Search : ${searchText.value}", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}



