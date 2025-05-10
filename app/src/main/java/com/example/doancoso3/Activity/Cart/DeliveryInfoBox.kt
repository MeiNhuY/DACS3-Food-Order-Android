package com.example.doancoso3.Activity.Cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.R
import com.example.doancoso3.ViewModel.OrderViewModel
import com.uilover.project2142.Helper.ManagmentCart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryInfoBox(onOrderPlaced: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("Tiền mặt") }

    val paymentMethods = listOf("Tiền mặt", "Thẻ tín dụng", "Ví điện tử")

    val orderViewModel = remember { OrderViewModel() }
    val context = LocalContext.current
    val cartManager = remember { ManagmentCart(context) }
    val cartItems = remember { cartManager.getListCart() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Thông Tin Người Nhận",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nhập tên người nhận
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.person),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Tên người nhận") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

// Nhập số điện thoại
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.phone),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.location),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                placeholder = { Text("Nhập địa chỉ giao hàng...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(R.color.orange),
                    cursorColor = colorResource(R.color.orange)
                )
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text(text = "Phương Thức Thanh Toán", fontSize = 14.sp, color = Color.Gray)

        paymentMethods.forEach { method ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { selectedPayment = method }
            ) {
                RadioButton(
                    selected = selectedPayment == method,
                    onClick = { selectedPayment = method },
                    colors = RadioButtonDefaults.colors(selectedColor = colorResource(R.color.orange))
                )
                Text(text = method, fontSize = 16.sp)
            }
        }
    }

    Button(
        onClick = {
            val latestCartItems = cartManager.getListCart()
            val latestTotalPrice = latestCartItems.sumOf { it.Price * it.numberInCart }

            if (name.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()) {
                orderViewModel.placeOrder(
                    recipientName = name,
                    phone = phone,
                    address = address,
                    paymentMethod = selectedPayment,
                    cartItems = latestCartItems,
                    totalPrice = latestTotalPrice,
                    onSuccess = {
                        cartManager.clearCart() // ✅ Xoá giỏ hàng
                        Toast.makeText(context, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show()
                    },
                    onError = {
                        Toast.makeText(context, "Lỗi: $it", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.orange)
        ),
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text = "Đặt Hàng",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}
