package com.example.kotlincoroutine.ViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutine.Api.ApiResponse
import com.example.kotlincoroutine.Repo.DataRepository
import com.example.kotlincoroutine.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userData = MutableStateFlow<List<User?>?>(value = emptyList())
    val userData: StateFlow<List<User?>?> = _userData

    private val _isDataLoaded = MutableStateFlow<Boolean>(value = false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded

    private val _isAdLoaded = savedStateHandle.getStateFlow("isAdLoaded", "Starting native AD......")
    val isAdLoaded: StateFlow<String> = _isAdLoaded


    private val _isFlowAdLoaded = MutableStateFlow<String>("Starting Flow ad...")
    val isFlowAdLoaded: StateFlow<String> = _isFlowAdLoaded


    private val _checkIntData = savedStateHandle.getStateFlow<Int>("checkIntData",0)
    val checkIntData: StateFlow<Int> = _checkIntData


    init {
        fetchUserData()
        fetchAd()
    }

    private fun fetchUserData() {
       viewModelScope.launch {
            dataRepository.fetchUserData().collect { (code, response) ->
                when (response) {
                    is ApiResponse.Failure -> {
                        _isDataLoaded.value = false
                    }

                    ApiResponse.Loading -> {
                        _isDataLoaded.value = false
                    }

                    is ApiResponse.Success -> {
                        _isDataLoaded.value = true
                        _userData.value = response.data
                    }
                }
            }
        }
    }

    private fun fetchAd() {
        viewModelScope.launch(Dispatchers.IO){
            delay(3000)
            savedStateHandle["isAdLoaded"]= "Ad has been loaded"
            _isFlowAdLoaded.value ="Flow Ad has been loaded"
        }
    }

    //change the value from Activity or Composable Screen
    fun changeIntData(value:Int):Int{
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            savedStateHandle["checkIntData"] = value
            _isFlowAdLoaded.value = "Ad has been replaced"
        }
        return _checkIntData.value
    }
}