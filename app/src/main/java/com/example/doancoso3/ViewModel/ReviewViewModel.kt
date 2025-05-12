package com.example.doancoso3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doancoso3.Domain.ReviewModel
import com.example.doancoso3.Repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ReviewViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _reviewList = MutableLiveData<List<ReviewModel>>()
    val reviewList: LiveData<List<ReviewModel>> = _reviewList

    // Hàm này vẫn có thể dùng nếu bạn đã biết userId và userName
    fun submitReview(
        foodId: Int,
        userId: String,
        userName: String,
        rating: Float,
        comment: String
    ) {
        repository.getReview(
            foodId = foodId,
            userId = userId,
            userName = userName,
            star = rating,
            comment = comment,
            onSuccess = { /* Optional: handle success */ },
            onFailure = { /* Optional: handle failure */ }
        )
    }

    // Hàm mới: Lấy UID hiện tại và userName từ Realtime Database
    fun submitReviewWithCurrentUser(
        foodId: Int,
        rating: Float,
        comment: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid ?: return

        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)
        userRef.get().addOnSuccessListener { snapshot ->
            val userName = snapshot.child("name").getValue(String::class.java) ?: "Anonymous"
            submitReview(foodId, uid, userName, rating, comment)
        }.addOnFailureListener {
            // Optional: xử lý lỗi nếu không lấy được dữ liệu
        }
    }

    fun loadReviews(foodId: Int) {
        repository.loadReviews(foodId).observeForever {
            _reviewList.value = it
        }
    }
}
