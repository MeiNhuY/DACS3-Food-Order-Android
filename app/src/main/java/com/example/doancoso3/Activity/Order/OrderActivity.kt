package com.example.doancoso3.Activity.Order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.doancoso3.ViewModel.OrderViewModel

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra("userId") ?: ""

        setContent {
            OrderListScreen(userId = userId)
        }
    }
}

