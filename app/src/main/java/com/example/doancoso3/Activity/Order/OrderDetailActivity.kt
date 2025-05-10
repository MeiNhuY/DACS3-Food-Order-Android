package com.example.doancoso3.Activity.Order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class OrderDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderId = intent.getStringExtra("orderId") ?: ""

        setContent {
            OrderDetailScreen(orderId = orderId, onBack = { finish() })
        }
    }
}

