package com.example.doancoso3.Activity.Splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doancoso3.Activity.Dashboard.MainScreen
import com.example.doancoso3.Repository.AuthService
import com.example.doancoso3.ViewModel.MainViewModel


@Composable
fun MyAppNavigation(modifier: Modifier = Modifier,
                    startDestination: String

                    ) {
    val navController = rememberNavController()



    NavHost(
        navController = navController,
        startDestination = startDestination,

        modifier = modifier
    ) {

        composable("login") {
            // Sử dụng viewModel() để lấy MainViewModel ở đây
            val viewModel: MainViewModel = viewModel()
            LoginScreen(modifier, navController,viewModel, authService = AuthService())
        }
        composable("signup") {
            val viewModel: MainViewModel = viewModel()
            SignupScreen(modifier, navController, viewModel, authService = AuthService())
        }
        composable("home") {
            val viewModel: MainViewModel = viewModel()
            MainScreen(modifier, navController, viewModel)
        }
    }
}


