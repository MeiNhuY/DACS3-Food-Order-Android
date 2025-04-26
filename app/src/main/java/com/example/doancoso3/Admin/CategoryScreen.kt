package com.example.doancoso3.Admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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

data class CategoryItem(
    val title: String,
    val restaurant: String,
    val imageRes: Int
)

@Composable
fun CategoryScreen(navController: NavHostController) {
    val items = remember {
        mutableStateListOf(
            CategoryItem("Noodles", "Waroenk kita", R.drawable.crab),
            CategoryItem("Hamburger", "Waroenk kita", R.drawable.noodles),
            CategoryItem("Pizza", "Waroenk kita", R.drawable.pizza),
            CategoryItem("Pizza", "Waroenk kita", R.drawable.pizza),
            CategoryItem("Pizza", "Waroenk kita", R.drawable.pizza),
            CategoryItem("Pizza", "Waroenk kita", R.drawable.pizza),
            CategoryItem("Pizza", "Waroenk kita", R.drawable.pizza),

            )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = "All Category",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                CategoryItemCard(item = item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("addCategoryScreen")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5835)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add Category", color = Color.Black)
        }
    }
}

@Composable
fun CategoryItemCard(item: CategoryItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFD5D4D4))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(25.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.Bold)
            Text(item.restaurant, color = Color.Gray, fontSize = 12.sp)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(onClick = { /* Edit logic */ }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { /* Delete logic */ }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryScreen() {
    val navController = rememberNavController()
    CategoryScreen(navController)
}

