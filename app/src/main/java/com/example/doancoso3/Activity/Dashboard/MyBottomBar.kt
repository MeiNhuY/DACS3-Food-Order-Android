package com.example.doancoso3.Activity.Dashboard


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.material.BottomAppBar // Material 2
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.doancoso3.Activity.Cart.CartActivity
import com.example.doancoso3.Activity.Order.OrderActivity
import com.example.doancoso3.Activity.Order.OrderListScreen
import com.example.doancoso3.Activity.Profile.ProfileActivity
import com.example.doancoso3.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MyBottomBar(navController: NavController) {
    val bottomMenuItemsList= prepareBottomMenu()
    val context= LocalContext.current
    var selectedItem by remember{ mutableStateOf("Home") }

    BottomAppBar(
        backgroundColor = colorResource(R.color.grey),
        elevation = 3.dp
    ) {
        bottomMenuItemsList.forEach { bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem==bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    when (bottomMenuItem.label) {
                        "Cart" -> {
                            context.startActivity(Intent(context, CartActivity::class.java))
                        }
                        "Profile" -> {
                            context.startActivity(Intent(context, ProfileActivity::class.java))
                        }
                        "Order" -> {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            if (userId != null) {
                                val intent = Intent(context, OrderActivity::class.java)
                                intent.putExtra("userId", userId)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        "Favorite" -> {
                            navController.navigate("favorite")
                        }

                        else -> {
                            Toast.makeText(context, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                ,

                icon = {
                    Icon(painter= bottomMenuItem.icon as Painter,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top=8.dp)
                            .size(20.dp))
                }
            )
        }
    }
}

data class BottomMenuItem(
    val label: String,
    val icon: Painter
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    return listOf(
        BottomMenuItem(label = "Home", icon = painterResource(R.drawable.btn_1)),
        BottomMenuItem(label = "Cart", icon = painterResource(R.drawable.btn_2)),
        BottomMenuItem(label = "Favorite", icon = painterResource(R.drawable.btn_3)),
        BottomMenuItem(label = "Order", icon = painterResource(R.drawable.btn_4)),
        BottomMenuItem(label = "Profile", icon = painterResource(R.drawable.btn_5))
    )
}

