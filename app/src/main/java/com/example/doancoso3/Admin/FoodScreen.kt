package com.example.doancoso3.Admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.doancoso3.R

// Sample data model
data class FoodItem(
    val name: String,
    val time: String,
    val rating: Float,
    val price: String,
    val imageRes: Int,
    val category: String
)

@Composable
fun FoodScreen(navController: NavHostController) {
    val allItems = listOf(
        FoodItem("Margherita", "15 min", 4.5f, "$10.99", R.drawable.pizza, "Pizza"),
        FoodItem("Pepperoni Lovers", "18 min", 4.7f, "$12.99", R.drawable.noodles, "Pizza"),
        FoodItem("Veggie Supreme", "20 min", 4.5f, "$11.99", R.drawable.crab, "Pizza"),
        FoodItem("BBQ Chicken", "22 min", 4.6f, "$13.99", R.drawable.pizza, "Pizza"),
        FoodItem("Hawaiian", "17 min", 4.3f, "$11.99", R.drawable.pizza, "Pizza"),
        FoodItem("Cheese Burger", "15 min", 4.2f, "$9.99", R.drawable.crab, "Burger"),
        FoodItem("Spicy Noodles", "10 min", 4.4f, "$8.49", R.drawable.noodles, "Noodles")
    )

    val categories = listOf("All", "Pizza", "Burger", "Noodles")
    var selectedCategory by remember { mutableStateOf("All") }
    var expanded by remember { mutableStateOf(false) }

    val filteredItems = if (selectedCategory == "All") allItems else allItems.filter { it.category == selectedCategory }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFDFDFD))
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {  // <-- gọi popBackStack để quay lại
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = "Food Menu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )

            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Food list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredItems) { item ->
                FoodCard(item = item, onClick = {
                    navController.navigate("detailFoodScreen")
                })
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Add food button
        Button(

            onClick = {
                navController.navigate("addFoodScreen")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5835)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add Food", color = Color.White)
        }
    }
}


@Composable
fun FoodCard(item: FoodItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF2F2F2))
            .padding(10.dp)
            .clickable { onClick() } // Nhấn toàn bộ dòng để xem chi tiết
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccessTime, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.time, fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.rating.toString(), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(item.price, fontWeight = FontWeight.Bold)
        }

        // Icon cây bút
        IconButton(
            onClick = {
                onClick() // Chuyển màn hình chi tiết
            }
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewFoodScreen() {
    val navController = rememberNavController()
    FoodScreen(navController)
}
