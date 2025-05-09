package com.example.doancoso3.Domain

data class OrderModel(
    val orderId: String = "",
    val userId: String = "",
    val userName: String = "",
    val recipientName: String = "",
    val phone: String = "",
    val address: String = "",
    val items: List<FoodModel> = listOf(),
    val totalPrice: Double = 0.0,
    val paymentMethod: String = "",
    val status: String = "Chờ xác nhận",
    val timestamp: Long = System.currentTimeMillis()
)
