package com.example.kotlincoroutine.Modal.CastModal

import androidx.mediarouter.media.MediaRouter

open class Device(val id: String, val name: String?, val description: String?, val enable: Boolean)
data class ChromeCastDevice(val route: MediaRouter.RouteInfo) :
    Device(
        route.id,
        route.name,
        route.description,
        route.isEnabled
    )
