package com.example.kotlincoroutine.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
) : ViewModel() {

    private val _userData = MutableStateFlow<List<User?>?>(value = emptyList())
    val userData: StateFlow<List<User?>?> = _userData

    private val _isDataLoaded = MutableStateFlow<Boolean>(value = false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded

    init {
        fetchUserData()
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
}