package com.example.doancoso3.Admin


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RevenueScreen() {
    var selectedFilter by remember { mutableStateOf("Filter") }
    var expanded by remember { mutableStateOf(false) }

    val filters = listOf("Ngày", "Tuần", "Tháng")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Doanh thu",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Box {
                Text(
                    text = selectedFilter,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { expanded = true }
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    color = Color.Black
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filters.forEach { filter ->
                        DropdownMenuItem(
                            text = { Text(filter) },
                            onClick = {
                                selectedFilter = filter
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Tổng doanh thu $selectedFilter này", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (selectedFilter) {
                        "Ngày" -> "5,000,000đ"
                        "Tuần" -> "30,000,000đ"
                        else -> "120,000,000đ"
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Biểu đồ doanh thu theo $selectedFilter", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        RevenueBarChart(
            data = when (selectedFilter) {
                "Ngày" -> listOf(2, 4, 3, 5, 1)
                "Tuần" -> listOf(30, 45, 60, 80)
                else -> listOf(100, 120, 140, 160)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Giao dịch gần đây", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        RecentTransactionItem("Ngày 20/4", "15,000,000đ")
        RecentTransactionItem("Ngày 19/4", "10,500,000đ")
        RecentTransactionItem("Ngày 18/4", "9,200,000đ")
    }
}

@Composable
fun RevenueBarChart(data: List<Int>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEachIndexed { index, value ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .height((value * 1.5).dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF81C784))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("T${index + 1}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun RecentTransactionItem(date: String, amount: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(date)
            Text(amount, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RevenueScreenPreview() {
    RevenueScreen()
}
