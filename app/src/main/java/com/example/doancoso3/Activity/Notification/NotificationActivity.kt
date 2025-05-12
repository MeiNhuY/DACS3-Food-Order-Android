package com.example.doancoso3.Activity.Notification

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.doancoso3.Activity.Order.formatTimestamp
import com.example.doancoso3.Domain.OrderModel
import com.example.doancoso3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseDatabase.getInstance().reference
    var orders by remember { mutableStateOf<List<OrderModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        userId?.let { uid ->
            db.child("Users").child(uid).child("role").get()
                .addOnSuccessListener { snapshot ->
                    val role = snapshot.getValue(String::class.java)

                    val orderRef = db.child("Order")
                    val listener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val orderList = mutableListOf<OrderModel>()
                            for (child in dataSnapshot.children) {
                                val order = child.getValue(OrderModel::class.java)
                                if (order != null) {
                                    // Nếu là user thì chỉ lấy đơn của chính mình
                                    if (role == "admin" || order.userId == uid) {
                                        orderList.add(order)
                                    }
                                }
                            }
                            orders = orderList.sortedByDescending { it.timestamp }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("NotificationScreen", "DB error: ${error.message}")
                        }
                    }

                    orderRef.addValueEventListener(listener)
                }
                .addOnFailureListener {
                    Log.e("NotificationScreen", "Failed to load role: ${it.message}")
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thông báo", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { innerPadding ->
        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Bạn chưa có thông báo nào.")
            }
        } else {



        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(orders) { order ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        // Hiển thị ảnh sản phẩm đầu tiên (nếu có)
                        val imageRes = R.drawable.thongbao // ảnh mặc định
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 12.dp)
                        )

                        Column {
                            Text(
                                text = "Bạn có 1 thông báo mới ",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Đơn hàng ${order.orderId} ${order.status}.",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "Hãy đánh giá sản phẩm trước ngày ${getReviewDeadline(order.timestamp)} để nhận 200 xu và giúp người dùng khác hiểu hơn về sản phẩm nhé!",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = formatTimestamp(order.timestamp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
            }
        }
    }
}

@Composable
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
@Composable
fun getReviewDeadline(timestamp: Long): String {
    val reviewDeadline = Calendar.getInstance().apply {
        timeInMillis = timestamp
        add(Calendar.DAY_OF_MONTH, 30) // ví dụ: 30 ngày để đánh giá
    }
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(reviewDeadline.time)
}
@Composable
fun getStatusColor(status: String?): Color {
    return when (status?.lowercase(Locale.getDefault())) {
        "đang giao", "processing" -> Color(0xFF4CAF50) // xanh lá
        "đã giao", "completed" -> Color(0xFF2196F3) // xanh dương
        "đã hủy", "cancelled" -> Color(0xFFF44336) // đỏ
        else -> MaterialTheme.colorScheme.primary
    }
}
