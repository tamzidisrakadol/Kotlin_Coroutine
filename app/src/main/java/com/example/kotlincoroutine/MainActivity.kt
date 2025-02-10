package com.example.kotlincoroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.kotlincoroutine.Modal.ProductUiState
import com.example.kotlincoroutine.ViewModel.ProductViewModel
import com.example.kotlincoroutine.ViewModel.UserViewModel
import com.example.kotlincoroutine.ui.theme.KotlinCoroutineTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "adol"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinCoroutineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CheckLifeCycle(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    //exception handling
    //launch -> launch return a job object. in other words it work like fire and forgot. In terms of exception handling launch cannot handle exception it crashes the application
    //async -> async return a deferred object. in other words it work like async await. In terms of exception handling async can handle exception it silently catch the application

    //This is a better way to catch exception using launch
    private fun exceptionHandlingUsingLaunch() {
        //CoroutineExceptionHandler
        val handler = CoroutineExceptionHandler { context, thorwable ->
            Log.d(TAG, "${thorwable.message}")
        }

        lifecycleScope.launch(Dispatchers.IO + handler) {
            launch {
                delay(2000)
                Log.d(TAG, "${Thread.currentThread().name}")
                throw Exception("Error occurred")
            }
        }
    }

    //handling exception using custom coroutine scope
    private fun exceptionHandlingUsingCustomCoroutineScope() {
        val handler = CoroutineExceptionHandler { context, thorwable ->
            Log.d(TAG, "${thorwable.message}")
        }

        //custom coroutineScope without Supervisor Job
        val customCoroutine =
            CoroutineScope(Dispatchers.IO + handler + CoroutineName("CustomCoroutine"))

        //custom coroutineScope with Supervisor Job
        val customCoroutine2 =
            CoroutineScope(Dispatchers.IO + handler + SupervisorJob() + CoroutineName("CustomCoroutine2"))

        customCoroutine.launch {
            //if we don't use supervisorScope then all  child coroutine and parent coroutine will get cancelled
            supervisorScope {  //child coroutine 1
                launch {
                    delay(2000)
                    Log.d(TAG, "${Thread.currentThread().name}")
                    throw Exception("Error occurred")
                }.join()
                //child coroutine 2
                launch {
                    delay(2000)
                    Log.d(TAG, "${Thread.currentThread().name}")
                }
            }

        }

    }

}

@Composable
fun CheckLifeCycle(modifier: Modifier = Modifier, userViewModel: UserViewModel = hiltViewModel()) {
    val isAddLoaded = userViewModel.isAdLoaded.collectAsState()
    val isFlowAdLoaded = userViewModel.isFlowAdLoaded.collectAsState()
    val checkIntData = userViewModel.checkIntData.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "check Ad : ${isAddLoaded.value}", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = modifier.height(30.dp))
        Text(
            text = "check flow Ad : ${isFlowAdLoaded.value}",
            color = Color.White,
            fontSize = 20.sp
        )
        Spacer(modifier = modifier.height(20.dp))
        Button(onClick = {
            userViewModel.changeIntData(200)
        }) {
            Text("click me")
        }
        Text(text = "Int no. ${checkIntData.value}", color = Color.White, fontSize = 20.sp)
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserList(modifier: Modifier = Modifier, userViewModel: UserViewModel) {
    val userData = userViewModel.userData.collectAsStateWithLifecycle()
    val isDataLoaded = userViewModel.isDataLoaded.collectAsStateWithLifecycle()


    if (isDataLoaded.value) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(userData.value ?: emptyList()) { index, item ->
                ListItem(text = item?.email)
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading Data", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun ListItem(modifier: Modifier = Modifier, text: String?) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text ?: "", color = Color.Green, fontSize = 20.sp)
    }
}
