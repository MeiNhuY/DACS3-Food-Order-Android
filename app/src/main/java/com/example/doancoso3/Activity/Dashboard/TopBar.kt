package com.example.doancoso3.Activity.Dashboard


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.doancoso3.R
import com.example.doancoso3.Activity.Cart.CartActivity
import com.example.doancoso3.Activity.Dashboard.Chat.ChatActivity
import com.example.doancoso3.Activity.Order.OrderActivity
import com.example.doancoso3.Activity.Profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TopBar(navController: NavController? = null) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .padding(top = 48.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val (name, settings, notification) = createRefs()

        // Settings icon with menu
        Column(modifier = Modifier.constrainAs(settings) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }) {
            Image(
                painter = painterResource(R.drawable.settings_icon),
                contentDescription = null,
                modifier = Modifier
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cart") },
                    onClick = {
                        context.startActivity(Intent(context, CartActivity::class.java))
//                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.btn_2), // same as BottomMenuItem
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("Favorite") },
                    onClick = {
                        navController?.navigate("favorite")
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.btn_3),
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("Order") },
                    onClick = {
                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                        if (userId != null) {
                            val intent = Intent(context, OrderActivity::class.java)
                            intent.putExtra("userId", userId)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show()
                        }
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.btn_4),
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("Profile") },
                    onClick = {
                        context.startActivity(Intent(context, ProfileActivity::class.java))
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.btn_5),
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("ChatAI") },
                    onClick = {
                        context.startActivity(Intent(context, ChatActivity::class.java))
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.chatgpt),
                            contentDescription = null
                        )
                    }
                )

            }
        }

        // App name
        Column(
            modifier = Modifier.constrainAs(name) {
                top.linkTo(parent.top)
                start.linkTo(settings.end)
                end.linkTo(notification.start)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Red)) { append("GOBBLE") }
                    withStyle(style = SpanStyle(color = Color.Black)) { append("FOOD") }
                },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(text = "Online Shop", color = Color.DarkGray, fontSize = 14.sp)
        }

        // Notification icon
        Image(
            painter = painterResource(R.drawable.bell_icon),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(notification) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .clickable { }
        )
    }
}
