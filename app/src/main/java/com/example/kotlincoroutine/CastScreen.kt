package com.example.kotlincoroutine

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.example.kotlincoroutine.Modal.CastModal.Device
import com.example.kotlincoroutine.ViewModel.CastingState
import com.example.kotlincoroutine.ViewModel.MediaRouterViewModel

@OptIn(UnstableApi::class)
@Composable
fun CastScreen(modifier: Modifier = Modifier) {
    val mediaRouterViewModel = hiltViewModel<MediaRouterViewModel>()
    val castingState = mediaRouterViewModel.castingState.collectAsStateWithLifecycle()
    //val castDeviceList = mediaRouterViewModel.castDeviceList.collectAsStateWithLifecycle()
    val selectedDevice = mediaRouterViewModel.selectedDevice.observeAsState()
    val deviceList = mediaRouterViewModel.deviceList

    when(castingState.value){
        CastingState.SEARCHING -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .semantics(mergeDescendants = true) {}
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    itemsIndexed(deviceList){index,item ->
                        DeviceItem(name = item.name ?: "Casting Device No name") {
                            mediaRouterViewModel.connect(item)
                        }

                    }
                }


            }



        }
        CastingState.CONNECTING -> {
            Column(modifier=Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Connecting...",
                    color = Color.White, modifier = Modifier.padding(10.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFe5c70d)
                )
            }
        }
        CastingState.CONNECTED -> {

        }
    }

}


@Composable
fun DeviceItem(name:String,castItemClick:()->Unit){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .clickable {
            castItemClick()
        }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(imageVector = Icons.Default.Phone, contentDescription = "")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = name, fontSize = 18.sp)
        }
    }
}