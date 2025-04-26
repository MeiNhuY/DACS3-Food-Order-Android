package com.example.doancoso3.Admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.doancoso3.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(onBack: () -> Unit) {
    var categoryCode by remember { mutableStateOf("") }
    var categoryName by remember { mutableStateOf("") }
    var categoryImage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = "Add Category",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category Code
        OutlinedTextField(
            value = categoryCode,
            onValueChange = { categoryCode = it },
            label = { Text("Category Code") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Name
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text("Category Name") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Image
        Text("Category Image", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.pizza),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
            )

            IconButton(
                onClick = {
                    // TODO: Mở bộ chọn ảnh (gallery hoặc camera)
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp)
                    .background(Color(0xAAFFFFFF), shape = RoundedCornerShape(50))
                    .zIndex(1f) // Đảm bảo hiển thị phía trên ảnh
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Image",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Save logic: categoryCode, categoryName, categoryImage
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5835)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add Category", color = Color.White)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddCategoryScreen() {
    AddCategoryScreen(onBack = {})
}
