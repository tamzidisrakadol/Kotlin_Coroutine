package com.example.kotlincoroutine.ViewModel

import CheckNetworkConnectivity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutine.Api.ApiResponse
import com.example.kotlincoroutine.Modal.Product
import com.example.kotlincoroutine.Modal.ProductModal
import com.example.kotlincoroutine.Modal.ProductUiState
import com.example.kotlincoroutine.Repo.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepo: ProductRepo,
    @ApplicationContext context: Context,
    val savedStateHandle: SavedStateHandle,
    val application: Application
) : ViewModel() {

    val productModalUiState: StateFlow<ProductUiState<ProductModal>> =
        savedStateHandle.getStateFlow("productModal", ProductUiState.Loading)

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts = _allProducts.asStateFlow()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow<String>("")
    val searchText = _searchText.asStateFlow()


    @OptIn(FlowPreview::class)
    val filterProducts = searchText
        .debounce(300)
        .map{query->
            if (query.isEmpty()) _allProducts.value
            else _allProducts.value.filter { it.title.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            productRepo.showAllProduct().collect { (code, response) ->
                when (response) {
                    is ApiResponse.Failure -> {
                        savedStateHandle["productModal"] =
                            ProductUiState.Error(message = response.msg, errorCode = code)
                    }

                    ApiResponse.Loading -> {
                        savedStateHandle["productModal"] = ProductUiState.Loading
                    }

                    is ApiResponse.Success -> {
                        savedStateHandle["productModal"] = ProductUiState.Success(data = response.data)
                        _allProducts.value = response.data?.products ?: emptyList()
                    }
                }
            }
        }
    }

    fun updateSearchText(text: String) {
        if (_searchText.value != text) {
            _searchText.value = text
        }
    }

}