package com.example.kotlincoroutine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlincoroutine.Modal.Product
import com.example.kotlincoroutine.ViewModel.NetworkViewModel
import com.example.kotlincoroutine.ViewModel.ProductViewModel

@Composable
fun SearchScreen(
    productViewModel: ProductViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel()
) {

    val filterProduct = productViewModel.filterProducts.collectAsStateWithLifecycle()
    val checkNetworkConnectivity = networkViewModel.isConnected.collectAsStateWithLifecycle()
    val searchText = productViewModel.searchText.collectAsStateWithLifecycle()

    when (checkNetworkConnectivity.value) {
        true -> {
            SearchScreenItem(
                listProduct = filterProduct.value,
                searchQuery = searchText.value,
                onSearchQueryChange = {
                    productViewModel.updateSearchText(it)
                })
        }

        false -> {
            NoNetworkScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenItem(
    modifier: Modifier = Modifier,
    listProduct: List<Product>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    var isActive = remember { mutableStateOf(false) }
    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        query = searchQuery,
        onQueryChange = { searchText ->
            onSearchQueryChange(searchText)
        },
        onSearch = {
            isActive.value = false
        },
        placeholder = {
            Text("Search Product")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            if (isActive.value) Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = modifier.clickable {
                    isActive.value = false
                })
        },
        active = isActive.value,
        onActiveChange = {
            isActive.value = it
        },
        content = {
            ListProduct(productList = listProduct)
        }
    )
}



