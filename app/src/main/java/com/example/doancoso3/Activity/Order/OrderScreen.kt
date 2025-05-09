package com.example.doancoso3.Activity.Order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.Domain.OrderModel
import com.example.doancoso3.ViewModel.OrderViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun OrderListScreen(userId: String, viewModel: OrderViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    LaunchedEffect(userId) {
        println("Fetching orders for userId: $userId")
        viewModel.fetchOrdersForUser(userId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Đơn hàng của bạn", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        if (viewModel.orders.isEmpty()) {
            Text("Bạn chưa có đơn hàng nào.")
        } else {
            LazyColumn {
                items(viewModel.orders) { order ->
                    OrderCard(order = order)
                }
            }
        }

    }
}
@Composable
fun OrderCard(order: OrderModel) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mã đơn: ${order.orderId}", fontWeight = FontWeight.Bold)
            Text("Tổng tiền: ${order.totalPrice}₫")
            Text("Trạng thái: ${order.status}", color = getStatusColor(order.status))
            Text("Ngày đặt: ${formatTimestamp(order.timestamp)}")
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "Chờ xác nhận" -> Color.Gray
        "Đã xác nhận" -> Color.Blue
        "Đang giao" -> Color(0xFF00C853)
        "Đã giao" -> Color.Green
        "Đã hủy" -> Color.Red
        else -> Color.Black
    }
}

