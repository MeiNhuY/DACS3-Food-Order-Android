package com.example.adminapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
fun DetailFoodScreen(onBack: () -> Unit) {
    val categoryOptions = listOf("Pizza", "Burger", "Noodle", "Salad", "Drinks")
    var expanded by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf("Pizza") }
    var itemName by remember { mutableStateOf("Cheese Pizza") }
    var itemPrice by remember { mutableStateOf("8.99") }
    var itemDescription by remember { mutableStateOf("A delicious cheese pizza with crispy crust.") }

    var editName by remember { mutableStateOf(false) }
    var editPrice by remember { mutableStateOf(false) }
    var editDescription by remember { mutableStateOf(true) } // Cho phép chỉnh sửa mô tả luôn

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
                text = "Detail Food",
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category Dropdown
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categoryOptions.forEach { category ->
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

        Spacer(modifier = Modifier.height(8.dp))

        // Item Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (editName) {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                Text(text = "Name: $itemName", modifier = Modifier.weight(1f))
            }
            IconButton(onClick = { editName = !editName }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Name")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Item Price
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (editPrice) {
                OutlinedTextField(
                    value = itemPrice,
                    onValueChange = { itemPrice = it },
                    label = { Text("Item Price") },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                Text(text = "Price: $$itemPrice", modifier = Modifier.weight(1f))
            }
            IconButton(onClick = { editPrice = !editPrice }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Price")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Item Image", fontWeight = FontWeight.Bold)
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
                modifier = Modifier.matchParentSize()
            )

            IconButton(
                onClick = {
                    // TODO: Open image picker
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp)
                    .background(Color(0xAAFFFFFF), shape = RoundedCornerShape(50))
                    .zIndex(1f)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Image", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Short Description", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            IconButton(onClick = { editDescription = !editDescription }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Description")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        if (editDescription) {
            OutlinedTextField(
                value = itemDescription,
                onValueChange = { itemDescription = it },
                placeholder = { Text("Write something...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                maxLines = 3
            )
        } else {
            Text(
                text = itemDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp),
                color = Color.DarkGray
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        Row {
            Button(
                onClick = {
                    // TODO: Save edited info (selectedCategory, itemName, itemPrice, itemDescription)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5835)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Edit", color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    // TODO: Handle delete logic
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Delete", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDetailFoodScreen() {
    DetailFoodScreen(onBack = {})
}
