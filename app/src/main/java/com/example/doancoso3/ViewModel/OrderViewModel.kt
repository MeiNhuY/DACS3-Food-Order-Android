package com.example.doancoso3.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.Domain.OrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class OrderViewModel : ViewModel() {

    fun placeOrder(
        recipientName: String,
        phone: String,
        address: String,
        paymentMethod: String,
        cartItems: List<FoodModel>,
        totalPrice: Double,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            onError("Người dùng chưa đăng nhập")
            return
        }

        val orderId = UUID.randomUUID().toString()
        val order = OrderModel(
            orderId = orderId,
            userId = currentUser.uid,
            userName = currentUser.displayName ?: "",
            recipientName = recipientName,
            phone = phone,
            address = address,
            items = cartItems,
            totalPrice = totalPrice,
            paymentMethod = paymentMethod
        )

        val dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.child(orderId)
            .setValue(order)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Lỗi không xác định") }
    }

    private val _orders = mutableStateListOf<OrderModel>()
    val orders: List<OrderModel> = _orders
    fun fetchOrdersForUser(userId: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Order")
        dbRef.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _orders.clear()
                    println("Tìm thấy ${snapshot.childrenCount} đơn hàng")
                    for (orderSnapshot in snapshot.children) {
                        println("Đơn hàng raw: ${orderSnapshot.value}")
                        val order = orderSnapshot.getValue(OrderModel::class.java)
                        order?.let {
                            _orders.add(it)
                            println("Thêm đơn hàng: ${it.orderId}")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Firebase error: ${error.message}")
                }
            })
    }

}
