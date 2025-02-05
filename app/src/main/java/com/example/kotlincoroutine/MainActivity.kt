package com.example.kotlincoroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kotlincoroutine.ViewModel.UserViewModel
import com.example.kotlincoroutine.ui.theme.KotlinCoroutineTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinCoroutineTheme {
                val userViewModel = hiltViewModel<UserViewModel>()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserList(
                        modifier = Modifier.padding(innerPadding),
                        userViewModel = userViewModel
                    )
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserList(modifier: Modifier = Modifier,userViewModel: UserViewModel) {
    val userData = userViewModel.userData.value
    val isDataLoaded = userViewModel.isDataLoaded.value

    Log.d("post","Bool check : $isDataLoaded")

    LazyColumn (
        modifier = modifier.fillMaxSize()
    ){
        itemsIndexed(userData ?: emptyList()){  index, item ->
            ListItem(text = item?.email)
        }
    }

}

@Composable
fun ListItem(modifier: Modifier = Modifier, text:String?) {
    Box(modifier = modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.CenterStart){
        Text(text = text ?: "", color = Color.Green, fontSize = 20.sp)
    }
}
