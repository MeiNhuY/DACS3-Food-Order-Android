package com.example.doancoso3.Activity.Favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.ViewModel.FavoriteViewModel
import android.content.Intent
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.platform.LocalContext
import com.example.doancoso3.Activity.DetailEachFood.DetailEachFoodActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(),
    onItemClick: (FoodModel) -> Unit = {},
    onBackClick: () -> Unit = {},
    navController: NavController,

) {
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }
    val context = LocalContext.current
    val items by remember { mutableStateOf(viewModel.favorites) }

    Column {
        // Thanh tiêu đề với nút quay lại
        TopAppBar(
            title = { Text("Sản phẩm yêu thích") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {  // Quay lại màn hình trước đó
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFFC5835))
        )
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(items) { item ->
                FavoriteItemCard(
                    food = item,
                    onClick = {
                        val intent = Intent(context, DetailEachFoodActivity::class.java)
                        intent.putExtra("object", item)
                        context.startActivity(intent)
                    },
                    onDeleteClick = {
                        viewModel.removeFavorite(item)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        // Hiển thị thông báo nếu không có món yêu thích nào
        if (items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Bạn chưa có sản phẩm yêu thích nào.", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onBackClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5835)) // Màu cam đẹp
                    ) {
                        Text("Quay lại", color = Color.White)
                    }
                }
            }
        } else {
        }
    }
}

@Composable
fun FavoriteItemCard(food: FoodModel, onClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hình ảnh món ăn
            Image(
                painter = rememberAsyncImagePainter(food.ImagePath),
                contentDescription = food.Title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            // Thông tin món ăn
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.Title,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF333333)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${food.Price} đ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFFC8C00)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = food.Description,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666)),
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Nút xoá
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Xoá", tint = Color.Black)
            }
        }
    }
}
