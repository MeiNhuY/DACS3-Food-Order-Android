package com.example.doancoso3.Activity.Splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doancoso3.Activity.Dashboard.MainScreen
import com.example.doancoso3.Activity.Favorite.FavoriteScreen
import com.example.doancoso3.Activity.Profile.ProfileScreen
import com.example.doancoso3.Repository.AuthService
import com.example.doancoso3.ViewModel.FavoriteViewModel
import com.example.doancoso3.ViewModel.MainViewModel
import com.example.doancoso3.ViewModel.ProfileViewModel

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    startDestination: String
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable("login") {
            val viewModel: MainViewModel = viewModel()
            LoginScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel,
                authService = AuthService()
            )
        }

        composable("signup") {
            val viewModel: MainViewModel = viewModel()
            SignupScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel,
                authService = AuthService()
            )
        }

        composable("home") {
            val viewModel: MainViewModel = viewModel()
            MainScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("favorite") {
            val favoriteViewModel: FavoriteViewModel = viewModel()
            FavoriteScreen(
                viewModel = favoriteViewModel,
                navController = navController
            )
        }

        composable("profile") {
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(
                viewModel = profileViewModel,
                navController = navController
            )
        }
    }
}
