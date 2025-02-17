package com.example.kotlincoroutine.ViewModel

import CheckNetworkConnectivity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    val application: Application
): ViewModel() {

    private val connectivity = CheckNetworkConnectivity(application)
    val isConnected: StateFlow<Boolean> = connectivity.networkStatusFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

}