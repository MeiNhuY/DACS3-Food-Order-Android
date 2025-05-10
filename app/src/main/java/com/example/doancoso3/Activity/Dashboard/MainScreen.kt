package com.example.doancoso3.Activity.Dashboard

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.doancoso3.Activity.DetailEachFood.DetailEachFoodActivity
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.ViewModel.MainViewModel


@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()

    val banners = remember { mutableStateListOf<BannerModel>() }
    var showBannerLoading by remember { mutableStateOf(true) }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showCategoryLoading by remember { mutableStateOf(true) }

    val authState = viewModel.authState.observeAsState()

    // Load filtered food list or all foods
    val foodList by viewModel.loadFiltered("...").observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    val categoryList by viewModel.loadCategory().observeAsState(emptyList())

    val context = LocalContext.current


    // Authentication check
    LaunchedEffect(authState.value) {
        when(authState.value) {
            is MainViewModel.AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    // Load banners
    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }
    }

    // Load categories
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
            item {
                Search { query ->
                    viewModel.searchFoodByName(query)
                }
            }
            if (searchResults.isNotEmpty()) {
                item {
                    Text("🔍 Kết quả tìm kiếm:", modifier = Modifier.padding(16.dp))
                }
                items(searchResults) { item ->
                    SearchFoodCard(item = item) { clickedItem ->
                        val intent = Intent(context, DetailEachFoodActivity::class.java)
                        intent.putExtra("object", clickedItem)
                        context.startActivity(intent)
                    }
                }
            }

            item { CategorySection(categories, showCategoryLoading) }
        }
    }

    Column {
        TextButton(onClick = {
            viewModel.signout()
        }) {
            Text(text = "Đăng Xuất")
        }
    }
}
