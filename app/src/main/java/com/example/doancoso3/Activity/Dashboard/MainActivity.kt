package com.example.doancoso3.Activity.Dashboard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.doancoso3.Activity.Splash.BaseActivity
import com.example.doancoso3.ViewModel.MainViewModel
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doancoso3.Activity.Splash.MyAppNavigation
import com.example.doancoso3.ui.theme.Doancoso3Theme


class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModels()  // Đã tạo ViewModel ở đây

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startDestination = intent.getStringExtra("startDestination") ?: "home"
        Log.d("MainActivity", "Start destination: $startDestination")

        setContent {
            Doancoso3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                        innerPadding ->
                    MyAppNavigation(
                        modifier = Modifier.padding(innerPadding),  // Không cần truyền viewModel nữa
                        startDestination = startDestination,

                        )
                }
            }
        }
    }
}

