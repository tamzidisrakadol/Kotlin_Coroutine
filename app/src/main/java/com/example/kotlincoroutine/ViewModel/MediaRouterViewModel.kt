package com.example.kotlincoroutine.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.mediarouter.media.MediaControlIntent
import androidx.mediarouter.media.MediaRouteSelector
import androidx.mediarouter.media.MediaRouter
import com.example.kotlincoroutine.Modal.CastModal.ChromeCastDevice
import com.example.kotlincoroutine.Modal.CastModal.Device
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.cast.framework.CastStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MediaRouterViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel(){

    private val _castingStateData = MutableStateFlow(CastingState.SEARCHING)
    val castingState = _castingStateData.asStateFlow()

    private val _castDeviceList = MutableStateFlow<List<Device>>(value = emptyList())
    val castDeviceList = _castDeviceList.asStateFlow()

    val deviceList = mutableStateListOf<Device>()

    private val _selectedDevice = MutableLiveData<MediaRouter.RouteInfo>()
    val selectedDevice: LiveData<MediaRouter.RouteInfo> = _selectedDevice


    private lateinit var castingContext: CastContext
//    private lateinit var castPlayer: CastPlayer
    private lateinit var mediaRouter: MediaRouter


    init {

        castingContext = CastContext.getSharedInstance(context)
        mediaRouter = MediaRouter.getInstance(context)
        castingContext.addCastStateListener(castStateListener())
        startSearch()
    }


    private fun castStateListener() = CastStateListener {
        when(it){
            CastState.CONNECTED -> {
                _castingStateData.value = CastingState.CONNECTED
                stopSearch()
            }

            CastState.NOT_CONNECTED -> {
                startSearch()
            }


        }
    }
    fun connect(device: Device) {
        _castingStateData.value = CastingState.CONNECTING
        stopSearch()

        when(device) {
            is ChromeCastDevice -> {
                mediaRouter.selectRoute(device.route)
            }
        }
    }



    fun stopSearch(){
        mediaRouter.removeCallback(mediaRouterCallBack())
    }

    private fun mediaRouterCallBack() =  object: MediaRouter.Callback() {
        override fun onRouteAdded(router: MediaRouter, route: MediaRouter.RouteInfo) {
            if (!deviceList.any { it.id == route.id } && route.isEnabled)
                deviceList.add(ChromeCastDevice(route = route))
        }

        override fun onRouteRemoved(router: MediaRouter, route: MediaRouter.RouteInfo) {
            val deviceById = deviceList.find { it.id == route.id }
            deviceList.remove(deviceById)
        }
    }

    fun startSearch(){
        val selector = MediaRouteSelector.Builder()
            .addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK)
            .build()

        mediaRouter.addCallback(selector, mediaRouterCallBack(), MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN)
        _castingStateData.value = CastingState.SEARCHING
    }

}


enum class CastingState {
    SEARCHING,
    CONNECTING,
    CONNECTED
}