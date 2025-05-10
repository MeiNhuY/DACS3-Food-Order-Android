package com.example.doancoso3.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.Domain.OrderModel
import com.google.firebase.Firebase
import com.google.firebase.database.*
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class MainRepository {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("Banners")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(BannerModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Bạn có thể log lỗi ở đây nếu muốn
            }
        })

        return listData
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi nếu cần
                TODO("Not yet implemented")
            }
        })
        return listData
    }

    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>> {
        val listData = MutableLiveData<MutableList<FoodModel>>()
        val ref = firebaseDatabase.getReference("Foods")
        val query: Query = ref.orderByChild("CategoryId").equalTo(id)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<FoodModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(FoodModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listData
    }


    fun searchFoodByName(query: String): LiveData<MutableList<FoodModel>> {
        val listData = MutableLiveData<MutableList<FoodModel>>()
        val ref = firebaseDatabase.getReference("Foods")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filteredList = mutableListOf<FoodModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(FoodModel::class.java)
                    if (item != null && item.Title.contains(query, ignoreCase = true)) {
                        filteredList.add(item)
                    }
                }
                listData.value = filteredList
            }

            override fun onCancelled(error: DatabaseError) {
                listData.value = mutableListOf() // Trả về list rỗng nếu lỗi
            }
        })

        return listData
    }


    fun getDetailOrder(
        orderId: String,
        onResult: (OrderModel?, Map<String, FoodModel>) -> Unit,
        onError: (String) -> Unit
    ) {
        val orderRef = firebaseDatabase.reference.child("Order").child(orderId)
        val foodRef = firebaseDatabase.reference.child("Foods")

        orderRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val order = snapshot.getValue(OrderModel::class.java)
                val foodIds = order?.items?.map { it.Id.toString() } ?: emptyList()

                if (foodIds.isEmpty()) {
                    onResult(order, emptyMap())
                    return@addOnSuccessListener
                }

                val foodDetails = mutableMapOf<String, FoodModel>()
                var count = 0

                foodIds.forEach { foodId ->
                    foodRef.child(foodId).get().addOnSuccessListener { foodSnap ->
                        if (foodSnap.exists()) {
                            val food = foodSnap.getValue(FoodModel::class.java)
                            food?.let { foodDetails[foodId] = it }
                        }
                        count++
                        if (count == foodIds.size) {
                            onResult(order, foodDetails)
                        }
                    }.addOnFailureListener {
                        count++
                        if (count == foodIds.size) {
                            onResult(order, foodDetails)
                        }
                    }
                }
            } else {
                onError("Không tìm thấy đơn hàng")
            }
        }.addOnFailureListener {
            onError("Lỗi: ${it.message}")
        }
    }
}
