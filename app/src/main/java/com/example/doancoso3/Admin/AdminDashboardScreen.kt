package com.example.doancoso3.Admin

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    pendingOrders: Int,
    completedOrders: Int,
    totalEarnings: String,
    onNavigate: (String) -> Unit
) {
    val navItems = listOf("Home", "Orders", "Profile")
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "GOBBLE FOOD",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF4CAF50)
                            )
                        )
                        Text(
                            text = "Fast Order – Big Flavor – GOBBLE Now!",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Xử lý khi nhấn thông báo */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF4CAF50)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, label ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                when (label) {
                                    "Home" -> Icons.Default.Home
                                    "Orders" -> Icons.Default.LocalShipping
                                    else -> Icons.Default.Person
                                },
                                contentDescription = label
                            )
                        },
                        label = { Text(label) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            if (label == "Profile") {
                                onNavigate("profile") // Điều hướng đến trang Admin Profile
                            }
                            if (label == "Orders") {
                                onNavigate("orders")
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Banner
            BannerSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Stat Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatCard("Pending", pendingOrders.toString(), Icons.Default.Schedule)
                StatCard("Completed", completedOrders.toString(), Icons.Default.CheckCircle)
                StatCard("Earnings", totalEarnings, Icons.Default.AttachMoney)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            val actions = listOf(
                Triple("Category", "category", Icons.Default.Category),
                Triple("Food", "food", Icons.Default.Fastfood),
                Triple("Revenue", "revenue", Icons.Default.Money),
                Triple("Orders", "orders", Icons.Default.LocalShipping),
            )

            actions.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { (label, route, icon) ->
                        ActionCard(label, icon, Modifier.weight(1f)) {
                            onNavigate(route)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Composable
fun StatCard(title: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier.size(width = 100.dp, height = 100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD0E3E2)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = title, tint = Color(0xFF00796B), modifier = Modifier.size(28.dp))
            Text(title, fontWeight = FontWeight.Medium)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun ActionCard(label: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF558B2F), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun BannerSection() {
    // Danh sách ảnh banner (đặt tên đúng với ảnh trong drawable)
    val bannerImages = listOf(
        R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { bannerImages.size })

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { page ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = bannerImages[page]),
                        contentDescription = "Banner Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop

                    )
                }
            }
        }

        // Dot indicator
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            repeat(bannerImages.size) { index ->
                val color = if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminDashboardScreenPreview() {
    AdminDashboardScreen(
        pendingOrders = 12,
        completedOrders = 34,
        totalEarnings = "$560",
        onNavigate = {}
    )
}
