package com.example.doancoso3.Domain


import java.io.Serializable

data class ReviewModel(
    val idReview: Int = 0,
    val foodId: String = "",                         // ID của món ăn được đánh giá
    val userId: String = "",                         // ID người dùng đánh giá
    val userName: String = "",                       // Tên người dùng (hiển thị)
    var Star: Double =0.0,                      // Số sao (0.0 đến 5.0)
    val comment: String = "",                        // Nội dung đánh giá
    val timestamp: Long = System.currentTimeMillis() // Thời gian gửi đánh giá
) : Serializable

