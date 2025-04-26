package com.example.doancoso3.Admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape

data class OrderItem(
    val name: String,
    val quantity: Int,
    val pricePerItem: Double
)

@Composable
fun OrderDetailScreen(orderId: String, onBack: () -> Unit) {
    // Sample Data
    val orderId = "DH123456"
    val customerName = "Nguyen Van A"
    val shippingAddress = "123 Đường ABC, Quận 1, TP.HCM"
    val orderDate = "26/04/2025"
    val paymentMethod = "Cash on Delivery"
    val orderItems = listOf(
        OrderItem("Pizza", 2, 150.0),
        OrderItem("Burger", 1, 80.0),
        OrderItem("Fries", 3, 30.0)
    )
    val totalAmount = orderItems.sumOf { it.quantity * it.pricePerItem }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Order Details",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0C57CC)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Order Info
        OrderInfoRow(label = "Order ID:", value = orderId)
        Spacer(modifier = Modifier.height(8.dp))
        OrderInfoRow(label = "Customer Name:", value = customerName)
        Spacer(modifier = Modifier.height(8.dp))
        OrderInfoRow(label = "Shipping Address:", value = shippingAddress)
        Spacer(modifier = Modifier.height(8.dp))
        OrderInfoRow(label = "Order Date:", value = orderDate)
        Spacer(modifier = Modifier.height(8.dp))
        OrderInfoRow(label = "Payment Method:", value = paymentMethod)

        Spacer(modifier = Modifier.height(24.dp))

        // Order Items List
        Text(
            text = "Products",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(orderItems) { item ->
                ProductRow(item)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Total
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "${totalAmount}  VND",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0C57CC)
            )
        }
    }
}


@Composable
fun OrderInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
        Text(text = value, fontWeight = FontWeight.Normal)
    }
}

@Composable
fun ProductRow(item: OrderItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFD7A888),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "x${item.quantity}",
                    fontSize = 14.sp,
                    color = Color(0xBD000000)
                )
            }
            Text(
                text = "${item.quantity * item.pricePerItem} VND",
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderDetailScreenPreview() {
    OrderDetailScreen(
        onBack = {},
        orderId = TODO()
    )
}
