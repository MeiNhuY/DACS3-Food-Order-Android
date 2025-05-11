package com.example.doancoso3.Activity.Order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.doancoso3.ViewModel.OrderViewModel

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra("userId") ?: ""

        // Set the Composable content inside the setContent block
        setContent {
            // Initialize NavController inside the Composable context
            val navController = rememberNavController()

            // Call the OrderListScreen within the Composable scope
            OrderListScreen(userId = userId, navController = navController)
        }
    }

}
