package com.example.doancoso3.Admin
// Imports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Model dữ liệu
data class Order(
    val idOrder: String,
    val date: String,
    val status: OrderStatus
)

enum class OrderStatus(val text: String) {
    Delivering("Đang giao hàng"),
    Received("Đã nhận hàng")
}

// Dữ liệu mẫu
val sampleOrders = listOf(
    Order("DH001", "24/04/2025", OrderStatus.Delivering),
    Order("DH002", "25/04/2025", OrderStatus.Received),
    Order("DH003", "26/04/2025", OrderStatus.Delivering)
)

// Giao diện một đơn hàng
@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    val (statusColor, statusIcon, backgroundColor) = when (order.status) {
        OrderStatus.Delivering -> Triple(Color(0xFF4CAF50), Icons.Filled.LocalShipping, Color(0xFFE8F5E9))
        OrderStatus.Received -> Triple(Color(0xFFE53935), Icons.Filled.CheckCircle, Color(0xFFFFEBEE))
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Mã đơn hàng: ${order.idOrder}", style = MaterialTheme.typography.titleMedium)
                Text("Ngày đặt hàng: ${order.date}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(order.status.text, style = MaterialTheme.typography.bodyMedium, color = statusColor)
                }
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(statusColor)
            )
        }
    }
}

// Giao diện danh sách đơn hàng
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    onBackClick: () -> Unit = {},
    onOrderClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF1F3))
    ) {
        TopAppBar(
            title = { Text("Đơn Hàng") },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                }
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleOrders) { order ->
                OrderCard(order = order, onClick = { onOrderClick(order.idOrder) })
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun OrdersScreenPreview() {
    OrdersScreen()
}
