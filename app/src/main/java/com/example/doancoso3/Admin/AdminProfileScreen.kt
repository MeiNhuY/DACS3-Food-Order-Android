package com.example.doancoso3.Admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import coil.compose.rememberImagePainter
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProfileScreen() {
    var avatarImageUri by remember { mutableStateOf<String?>(null) }
    var location by remember { mutableStateOf("Jaipur") }

    var isNameEditable by remember { mutableStateOf(false) }
    var isAddressEditable by remember { mutableStateOf(false) }
    var isEmailEditable by remember { mutableStateOf(false) }
    var isPhoneEditable by remember { mutableStateOf(false) }
    var isPasswordEditable by remember { mutableStateOf(false) }

    val getImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> uri?.let { avatarImageUri = it.toString() } }
    )

    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    var menuIconOffset by remember { mutableStateOf(Offset.Zero) }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Admin Profile",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    navigationIcon = {
                        Box(modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                menuIconOffset = coordinates.localToWindow(Offset.Zero)
                            }
                        ) {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black
                    )
                )

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    offset = DpOffset(x = 0.dp, y = 6.dp), // hiển thị ngay dưới icon
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("Đổi mật khẩu") },
                        onClick = { /* TODO */ menuExpanded = false },
                        leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Đăng xuất") },
                        onClick = { /* TODO */ menuExpanded = false },
                        leadingIcon = { Icon(Icons.Default.ExitToApp, contentDescription = null) }
                    )
                }
            }
        },
        bottomBar = {
            Button(
                onClick = { /* TODO: Save changes */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 20.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC5835))
            ) {
                Text("Save Information", color = Color.White, fontSize = 15.sp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Gray)
                    .clickable { getImageLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                avatarImageUri?.let {
                    Image(painter = rememberImagePainter(it), contentDescription = "Avatar")
                } ?: Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Choose Your Location",
                color = Color(0xFF009688),
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 2.dp)
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            InfoRow("Name", "Lorem Ipsum", isNameEditable) { isNameEditable = !isNameEditable }
            InfoRow("Address", "Lorem ipsum placeholder text.", isAddressEditable) { isAddressEditable = !isAddressEditable }
            InfoRow("Email", "example@gmail.com", isEmailEditable) { isEmailEditable = !isEmailEditable }
            InfoRow("Phone", "1234567890", isPhoneEditable) { isPhoneEditable = !isPhoneEditable }
            InfoRow("Password", "********", isPasswordEditable) { isPasswordEditable = !isPasswordEditable }
        }
    }
}




@Composable
fun InfoRow(label: String, value: String, isEditable: Boolean, onEditClick: () -> Unit) {
    var fieldValue by remember { mutableStateOf(value) }
    OutlinedTextField(
        value = fieldValue,
        onValueChange = { if (isEditable) fieldValue = it },
        label = { Text(label) },
        readOnly = !isEditable,
        trailingIcon = {
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit $label")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF4CAF50),
            unfocusedBorderColor = Color.LightGray,
            disabledBorderColor = Color.LightGray,
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5)
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminProfileScreenPreview() {
    AdminProfileScreen()
}
