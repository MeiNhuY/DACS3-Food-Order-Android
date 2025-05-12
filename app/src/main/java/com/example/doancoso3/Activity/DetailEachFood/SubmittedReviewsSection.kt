package com.example.doancoso3.Activity.DetailEachFood

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.Domain.ReviewModel


@Composable
fun SubmittedReviewsSection(reviews: List<ReviewModel>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Đánh giá",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (reviews.isEmpty()) {
            Text(
                text = "Chưa có đánh giá nào.",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        } else {
            reviews.forEach { review ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)), // Viền nhẹ
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            // Avatar mặc định
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "User Avatar",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 8.dp),
                                tint = Color.Gray
                            )

                            Column {
                                Text(
                                    text = review.userName,
                                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 16.sp),
                                    color = MaterialTheme.colors.onSurface // Màu sắc phù hợp với nền
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    repeat(5) { i ->
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = if (i < review.Star) Color(0xFFFFC107) else Color.Gray,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = review.comment,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
