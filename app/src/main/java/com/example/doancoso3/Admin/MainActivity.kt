package com.example.doancoso3.Admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminapp.DetailFoodScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            AdminAppTheme {
//                AppNavigation()
//            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "admin_dashboard"
    ) {
        composable("admin_dashboard") {
            AdminDashboardScreen(
                pendingOrders = 12,
                completedOrders = 34,
                totalEarnings = "$560",
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        //Category
        composable("category") {
            CategoryScreen(navController)
        }

        composable("addCategoryScreen") {
            AddCategoryScreen(onBack = { navController.popBackStack() })
        }


        //Food
        composable("food") {
            FoodScreen(navController)
        }

        composable("addFoodScreen") {
            AddFoodScreen(onBack = { navController.popBackStack() })
        }

        composable("detailFoodScreen") {
            DetailFoodScreen(onBack = { navController.popBackStack() })
        }



// Order list screen
        composable("orders") {
            OrdersScreen(
                onBackClick = { navController.popBackStack() },
                onOrderClick = { orderId ->
                    navController.navigate("orderDetailScreen/$orderId")
                }
            )
        }

// Order detail screen with parameter
        composable("orderDetailScreen/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderDetailScreen(orderId = orderId, onBack = { navController.popBackStack() })
        }





        //Revenue
        composable("revenue") {
            RevenueScreen()
        }


        //Profile
        composable("profile") {
            AdminProfileScreen() // Thêm màn hình profile
        }

    }
}




