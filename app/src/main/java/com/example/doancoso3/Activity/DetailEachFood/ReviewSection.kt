package com.example.doancoso3.Activity.DetailEachFood

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ReviewSection(
    rating: Float,
    comment: String,
    onRatingChanged: (Float) -> Unit,
    onCommentChanged: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Review",
            style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Chọn sao
        Row(
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            for (i in 1..5) {
                IconButton(onClick = { onRatingChanged(i.toFloat()) }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray
                    )
                }
            }
        }

        // Ô comment với nút Send bên trong
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
        ) {
            TextField(
                value = comment,
                onValueChange = onCommentChanged,
                placeholder = { Text("Write your comment...") },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 60.dp), // chừa chỗ cho nút Send
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Button(
                onClick = onSendClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .height(40.dp)
                    .zIndex(1f), // đảm bảo hiện lên trên TextField
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFC5835)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Send", color = Color.White)
            }
        }
    }
}
