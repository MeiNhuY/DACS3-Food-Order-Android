package com.example.doancoso3.Activity.Order

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.Domain.OrderModel
import com.example.doancoso3.ViewModel.MainViewModel
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import com.example.doancoso3.Activity.DetailEachFood.DetailEachFoodActivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: String,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(orderId) {
        viewModel.getDetailOrder(orderId)
    }

    val order = viewModel.orderDetailState
    val foodDetails = viewModel.foodDetailMap
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...")
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(errorMessage ?: "Đã xảy ra lỗi")
                }
            }

            order != null -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Customer information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2EDF3))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoRow(icon = Icons.Default.Person, label = "Recipient name", value = order.recipientName)
                            Spacer(modifier = Modifier.height(8.dp))
                            InfoRow(icon = Icons.Default.Phone, label = "Phone", value = order.phone)
                            Spacer(modifier = Modifier.height(8.dp))
                            InfoRow(icon = Icons.Default.LocationOn, label = "Address", value = order.address)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("List food ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    order.items.forEach { item ->
                        val food = foodDetails[item.Id.toString()]
                        food?.let {
                            val context = LocalContext.current // Lấy context ở đây

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        val intent = Intent(context, DetailEachFoodActivity::class.java)
                                        intent.putExtra("object", it) // Truyền nguyên FoodModel
                                        context.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(it.ImagePath),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = it.Title, fontWeight = FontWeight.Bold)
                                    }
                                    Text(
                                        text = "${it.Price} đ",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2C7A7B)
                                    )
                                }
                            }
                        }
                    }


                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    Text("Total: ${order.totalPrice} đ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Order status", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    val statusColor = when (order.status.lowercase()) {
                        "chờ xác nhận" -> Color(0xFF2C7A7B) // xanh
                        "đang giao" -> Color(0xFFFFA000)     // vàng
                        "đã giao" -> Color(0xFF388E3C)       // xanh lá
                        "đã hủy" -> Color(0xFFD32F2F)        // đỏ
                        else -> Color.Gray
                    }
                    Text(
                        text = order.status,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Order not found")
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        },
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledBorderColor = Color.LightGray,
            disabledLeadingIconColor = Color.Black,
            disabledLabelColor = Color.Gray
        )
    )
}
