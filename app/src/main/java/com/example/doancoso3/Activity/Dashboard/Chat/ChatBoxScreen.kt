package com.example.doancoso3.Activity.Dashboard.Chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ChatBoxScreen() {
    val viewModel = remember { ChatViewModel() }
    val aiResponse by viewModel.aiResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val ingredients = remember { mutableStateOf("") }
    val diet = remember { mutableStateOf("") }

    val portions = listOf("1 person", "2 person", "4 person", "6 person")
    val cuisines = listOf("Vietnam", "Korea", "Japan", "China")
    val levels = listOf("Easy", "Average", "Difficult")

    val selectedPortion = remember { mutableStateOf(portions[0]) }
    val selectedCuisine = remember { mutableStateOf(cuisines[0]) }
    val selectedLevel = remember { mutableStateOf(levels[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(top = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Row {
                Text(
                    text = "👩‍🍳",
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.width(4.dp))

                GradientText(text = "GOBBLE")

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "AI",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Not sure what to eat today? Let me suggest something for you!",
                fontSize = 16.sp,
                color = Color(0xFFFC5835),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormLabel("📝 Ingredients:")
            OutlinedTextField(
                value = ingredients.value,
                onValueChange = { ingredients.value = it },
                placeholder = { Text("Ex: Chicken") },
                modifier = Modifier.fillMaxWidth()
            )

            FormLabel("👨‍👩‍👧‍👦 Portion size:")
            DropdownMenuBox(options = portions, selected = selectedPortion)

            FormLabel("🍜 Cuisine:")
            DropdownMenuBox(options = cuisines, selected = selectedCuisine)

            FormLabel("🔥 Level:")
            DropdownMenuBox(options = levels, selected = selectedLevel)

            FormLabel("🥦 Level of cuisine:")
            OutlinedTextField(
                value = diet.value,
                onValueChange = { diet.value = it },
                placeholder = { Text("Dietary preferences (e.g., vegetarian, gluten-free...)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.getSuggestions(
                        ingredients = ingredients.value,
                        portion = selectedPortion.value,
                        cuisine = selectedCuisine.value,
                        level = selectedLevel.value,
                        diet = diet.value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFFFC5835)),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                    Text("Loading...", fontSize = 16.sp, color = Color.White)
                } else {
                    Text("Create the menu list now", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (aiResponse.isNotEmpty()) {
                Text(
                    text = aiResponse,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}



@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF000000),
        modifier = Modifier.padding(vertical = 10.dp)
    )
}


@Composable
fun DropdownMenuBox(options: List<String>, selected: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected.value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        )

        // Vùng clickable đè lên OutlinedTextField
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selected.value = option
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}




@Composable
fun GradientText(text: String, fontSize: TextUnit = 28.sp) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF0A0F7A), Color(0xFF59AAE1), Color(0xFFEC47BC))
    )

    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            brush = gradient // Đây là cách áp dụng gradient
        )
    )
}
