package com.example.doancoso3.Activity.Dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.doancoso3.ui.theme.Doancoso3Theme
import com.example.doancoso3.Activity.Splash.BaseActivity
import com.example.doancoso3.Domain.BannerModel
import androidx.compose.foundation.lazy.items
import com.example.doancoso3.ViewModel.MainViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.activity.viewModels
import com.example.doancoso3.Domain.CategoryModel


class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
    @Composable
    @Preview
    fun MainScreen() {
        val scaffoldState = rememberScaffoldState()
        val banners = remember { mutableStateListOf<BannerModel>() }

        var showBannerLoading by remember { mutableStateOf(true) }

        val categories = remember { mutableStateListOf<CategoryModel>() }
        var showCategoryLoading by remember { mutableStateOf(true) }

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
                item {
                    TopBar()
                }
                item {
                    Banner(banners, showBannerLoading)
                }
                item {
                    Search()
                }
                item {
                    CategorySection(categories, showCategoryLoading)
                }
            }
        }
    }
}
