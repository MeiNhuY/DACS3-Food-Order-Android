package com.example.doancoso3.Activity.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.R

@Composable
fun ProfileSettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Back button and title
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF3A3EFF), shape = RoundedCornerShape(12.dp))
                    .clickable { /* Handle back */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text("Profile Settings", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Your Profile Information", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Image with edit icon
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // tạm dùng ảnh này để không lỗi preview
                contentDescription = "Profile",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.LightGray, CircleShape)
            )
            Box(
                modifier = Modifier
                    .offset(x = (-4).dp, y = (-4).dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3A3EFF))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Personal Information", color = Color(0xFF6A6AD3), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))
        ProfileField(label = "Account Number", value = "3024982387")
        ProfileField(label = "Username", value = "Aryan.Stirk2")
        ProfileField(label = "Email", value = "aryan.stirk2nd@gmail.com")
        ProfileField(label = "Mobile Phone", value = "+620932938232")
        ProfileField(label = "Address", value = "Gotham Road 21...")

        Spacer(modifier = Modifier.height(24.dp))
        Text("Security", color = Color(0xFF6A6AD3), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))
        SecurityItem(title = "Change Pin")
        SecurityItem(title = "Change Password")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Finger Print", fontSize = 16.sp)
            Switch(
                checked = true,
                onCheckedChange = {},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF3A3EFF)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Other", color = Color(0xFF6A6AD3), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp)) // để tránh bị cắt khi preview
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = label, fontSize = 14.sp, color = Color(0xFF6A6AD3))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(text = value, color = Color.Gray)
        }
    }
}

@Composable
fun SecurityItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .background(Color(0xFFF1F1F1), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title)
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileSettingsScreen() {
    ProfileSettingsScreen()
}
