package com.example.kotlincoroutine


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.kotlincoroutine.Modal.Product
import com.example.kotlincoroutine.Modal.ProductModal
import com.example.kotlincoroutine.Modal.ProductUiState
import com.example.kotlincoroutine.ViewModel.NetworkViewModel
import com.example.kotlincoroutine.ViewModel.ProductViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel()
) {
    val productModalUIState = productViewModel.productModalUiState.collectAsStateWithLifecycle()
    val networkConnectivity = networkViewModel.isConnected.collectAsStateWithLifecycle()

    when (networkConnectivity.value) {
        true -> {
            ProductModalUiState(productModalUIState, onIconClick = {
                navController.navigate("searchScreen")
            })
        }

        false -> {
            NoNetworkScreen()
        }
    }
}

@Composable
fun NoNetworkScreen(modifier: Modifier= Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            modifier = modifier.size(25.dp),
            tint = Color.Black,
            contentDescription = ""
        )
        Text("No Internet Connection", fontSize = 20.sp, color = Color.Black)
    }
}

@Composable
private fun ProductModalUiState(
    productModalUIState: State<ProductUiState<ProductModal>>,
    modifier: Modifier = Modifier,
    onIconClick:()-> Unit
) {
    when (val state = productModalUIState.value) {
        is ProductUiState.Error -> {

            val errorData = state.message
            Column(
                modifier = modifier.fillMaxSize().background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = errorData, fontSize = 25.sp, color = Color.Black)
            }
        }

        ProductUiState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize().background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.Black
                )
            }
        }

        is ProductUiState.Success -> {
            val productData = state.data.products
            ProductScreenItem(productList = productData, onIconClick = onIconClick)
        }
    }
}


@Composable
private fun ProductScreenItem(modifier: Modifier = Modifier, productList: List<Product>,onIconClick:()-> Unit ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Spacer(modifier = modifier.height(50.dp))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(2.dp, Color.Black),
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Search here", fontSize = 15.sp, color = Color.LightGray)
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = Color.Black,
                    contentDescription = "",
                    modifier = modifier.clickable{
                        onIconClick()
                    }
                )
            }
        }
        Spacer(modifier = modifier.height(10.dp))
        ListProduct(productList = productList)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListProduct(modifier: Modifier = Modifier, productList: List<Product>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(productList) { product ->
            ProductItem(url = product.thumbnail, title = product.title, modifier = modifier.animateItem())
        }
    }
}


@Composable
private fun ProductItem(modifier: Modifier = Modifier, url: String, title: String) {
    Card(
        modifier = modifier
            .width(50.dp)
            .wrapContentHeight()
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp)),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )

    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = url,
                contentDescription = "",
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Text(text = title, fontSize = 18.sp, color = Color.Black)
        }
    }
}


