package com.example.kotlincoroutine.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepo: ProductRepo,
    @ApplicationContext context: Context,
    val savedStateHandle: SavedStateHandle
) : ViewModel(){

    val productList: StateFlow<ProductUiState<ProductModal>> = savedStateHandle.getStateFlow("testProductList", initialValue = ProductUiState.Loading)
    init {
        fetchProduct()
    }



    private fun fetchProduct(){
        viewModelScope.launch{
            productRepo.showAllProduct().collect{(code, response) ->
                when(response){
                    is ApiResponse.Failure -> {
                        savedStateHandle["testProductList"] = ProductUiState.Error(message = response.msg, errorCode = code)
                    }
                    ApiResponse.Loading -> {
                        savedStateHandle["testProductList"] = ProductUiState.Loading
                    }
                    is ApiResponse.Success -> {
                        savedStateHandle["testProductList"] = response.data
                    }
                }
            }
        }
    }
}