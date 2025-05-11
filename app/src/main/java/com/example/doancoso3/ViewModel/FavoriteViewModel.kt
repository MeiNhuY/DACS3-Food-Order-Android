package com.example.doancoso3.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.doancoso3.Domain.FoodModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FavoriteViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("Favorite")

    private val _favorites = mutableStateListOf<FoodModel>()
    val favorites: List<FoodModel> = _favorites

    // Tải danh sách yêu thích của người dùng
    fun loadFavorites() {
        val userId = auth.currentUser?.uid ?: return
        database.child(userId).get().addOnSuccessListener { snapshot ->
            _favorites.clear()
            snapshot.children.mapNotNullTo(_favorites) {
                it.getValue(FoodModel::class.java)
            }
        }
    }

    // Kiểm tra món ăn có phải yêu thích không
    fun isFavorite(foodId: Int): Boolean {
        return _favorites.any { it.Id == foodId }
    }

    // Thêm hoặc xóa món ăn khỏi danh sách yêu thích
    fun toggleFavorite(item: FoodModel) {
        val userId = auth.currentUser?.uid ?: return
        val ref = database.child(userId).child(item.Id.toString())

        if (isFavorite(item.Id)) {
            ref.removeValue()
            _favorites.removeIf { it.Id == item.Id }
        } else {
            ref.setValue(item)
            _favorites.add(item)
        }
    }
    // Xoá món ăn khỏi danh sách yêu thích
    fun removeFavorite(item: FoodModel) {
        val userId = auth.currentUser?.uid ?: return
        val ref = database.child(userId).child(item.Id.toString())

        ref.removeValue().addOnSuccessListener {
            _favorites.removeIf { it.Id == item.Id }
        }
    }

}

