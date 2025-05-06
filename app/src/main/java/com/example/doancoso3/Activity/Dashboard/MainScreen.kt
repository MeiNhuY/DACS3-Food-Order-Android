package com.example.doancoso3.Activity.Dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.ViewModel.AuthState
import com.example.doancoso3.ViewModel.MainViewModel

@Composable
fun MainScreen(modifier: Modifier, navController: NavController, viewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()
    val banners = remember { mutableStateListOf<BannerModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }

    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showCategoryLoading by remember { mutableStateOf(true) }

    val authState=viewModel.authState.observeAsState()

    LaunchedEffect (authState.value){
        when(authState.value){
            is AuthState.Unauthenticated-> navController.navigate("login")
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { MyBottomBar() }
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { TopBar() }
            item { Banner(banners, showBannerLoading) }
            item { Search() }
            item { CategorySection(categories, showCategoryLoading) }
        }
    }
    Column {
        TextButton(onClick = {
            viewModel.signout()
        }) {
            Text(text = "Dang Xuat")
        }
    }
}
