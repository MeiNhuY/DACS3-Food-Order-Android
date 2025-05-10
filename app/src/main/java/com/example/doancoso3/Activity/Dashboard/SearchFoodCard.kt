package com.example.doancoso3.Activity.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.doancoso3.Domain.FoodModel
import coil.compose.AsyncImage



@Composable
fun SearchFoodCard(item: FoodModel, onItemClick: (FoodModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable { onItemClick(item) } // <- xử lý click
    ) {
        AsyncImage(
            model = item.ImagePath, // URL ảnh Firebase
            contentDescription = item.Title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(text = item.Title, fontWeight = FontWeight.Bold)
            Text(text = "${item.TimeValue} min • ⭐ ${item.Star}", color = Color.Gray)
            Text(text = "$${item.Price}", fontWeight = FontWeight.SemiBold, color = Color.Black)
        }
    }
}
