package com.example.doancoso3.Activity.Order

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.doancoso3.Domain.OrderModel
import com.example.doancoso3.ViewModel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(userId: String, navController: NavController, viewModel: OrderViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    LaunchedEffect(userId) {
        println("Fetching orders for userId: $userId")
        viewModel.fetchOrdersForUser(userId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("Đơn hàng của bạn") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()  // Navigate back
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFFC8C00))
        )
        if (viewModel.orders.isEmpty()) {
            Text("Bạn chưa có đơn hàng nào.", style = MaterialTheme.typography.bodyMedium)
        } else {
            val context = LocalContext.current
            LazyColumn {
                items(viewModel.orders) { order ->
                    OrderCard(order = order) {
                        val intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("orderId", order.orderId)
                        context.startActivity(intent)
                    }
                }
            }
        }

    }
}

@Composable
fun OrderCard(order: OrderModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mã đơn: ${order.orderId}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text("Tổng tiền: ${order.totalPrice}₫", style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFFC8C00)))
            Text("Trạng thái: ${order.status}", color = getStatusColor(order.status), style = MaterialTheme.typography.bodySmall)
            Text("Ngày đặt: ${formatTimestamp(order.timestamp)}", style = MaterialTheme.typography.bodySmall)
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
