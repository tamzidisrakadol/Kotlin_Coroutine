package com.example.kotlincoroutine.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutine.Api.ApiResponse
import com.example.kotlincoroutine.Repo.DataRepository
import com.example.kotlincoroutine.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val _userData = MutableLiveData<List<User?>?>()
    val userData: LiveData<List<User?>?> = _userData

    private val _isDataLoaded = MutableLiveData<Boolean>()
    val isDataLoaded: LiveData<Boolean> = _isDataLoaded
    init {
        fetchUserData()
    }

     fun fetchUserData(){
        viewModelScope.launch{
            dataRepository.fetchUserData().collect{ (code, response) ->
                when(response){
                    is ApiResponse.Failure -> {
                        _isDataLoaded.value  = false
                        Log.d("Tag","${response.msg} code: $code")
                    }
                    ApiResponse.Loading -> {
                        _isDataLoaded.value  = false
                        Log.d("post","Loading")
                    }
                    is ApiResponse.Success -> {
                        Log.d("post","IsdataLoading true")
                        _isDataLoaded.value  = true
                        _userData.value = response.data

                    }
                }
            }
        }
    }

}