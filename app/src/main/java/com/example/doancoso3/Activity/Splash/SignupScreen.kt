package com.example.doancoso3.Activity.Splash


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.doancoso3.R
import com.example.doancoso3.Repository.AuthService
import com.example.doancoso3.ViewModel.AuthState
import com.example.doancoso3.ViewModel.MainViewModel
import com.example.doancoso3.ui.theme.AlegreyaFontFamily
import com.example.doancoso3.ui.theme.AlegreyaSansFontFamily
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainViewModel,
    authService: AuthService
) {
//    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val authState = viewModel.authState.observeAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var expanded by remember { mutableStateOf(false) }
    val roles= listOf("user", "admin", ) // Danh sách quyền
    var selectedRole by remember { mutableStateOf("user") } // Quyền mặc định

    // Biểu thức chính quy để kiểm tra cú pháp email
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )


    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Error-> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT)
            else->Unit
        }
   }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.darkBrown))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(350.dp)
                .offset(y = (-20).dp)
                .fillMaxWidth()
        ) {
            val (backgroundImg, logImg, loginText) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.intro_pic),
                contentDescription = null,
                modifier = Modifier
                    .height(900.dp)
                    .offset(y = (-80).dp)
                    .constrainAs(backgroundImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.pizza),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .offset(y = (-10).dp)
                    .constrainAs(logImg) {
                        top.linkTo(backgroundImg.top)
                        bottom.linkTo(backgroundImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.Fit
            )

            // Outline Text
            Text(
                text = "Đăng Ký",
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = AlegreyaFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    drawStyle = Stroke(width = 10f)
                ),
                modifier = Modifier
                    .constrainAs(loginText) {
                        top.linkTo(logImg.bottom, margin = (-50).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Filled Text (inner layer)
            Text(
                text = "Đăng Ký",
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = AlegreyaFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA500)
                ),
                modifier = Modifier
                    .constrainAs(createRef()) {
                        top.linkTo(logImg.bottom, margin = (-50).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        CTextField(
            hint = "Name",
            value = name,
            onValueChanged = {
                name=it
                errorMessage = null
            }
        )

        CTextField(
            hint = "Email",
            value = email,
            onValueChanged = {
                email = it
                errorMessage = null
            }
        )

        CTextField(
            hint = "Address",
            value = address,
            onValueChanged = {
                address = it
                errorMessage = null
            }
        )

        CTextField(
            hint = "Phone",
            value = phone,
            onValueChanged = {
                phone = it
                errorMessage = null
            }
        )


        CTextField(
            hint = "Password",
            value = password,
            onValueChanged = {
                password = it
                errorMessage = null
            }
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Role") },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roles.forEach { role ->
                    DropdownMenuItem(
                        onClick = {
                            selectedRole = role
                            expanded = false
                        },
                        text = { Text(role) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CButton(
                onClick = {
                    coroutineScope.launch {
                        // Kiểm tra các trường
                        if (name.isBlank() || email.isBlank() || password.isBlank()) {
                            errorMessage = "Please fill in all fields"
                            return@launch
                        }

                        if (!emailPattern.matcher(email).matches()) {
                            errorMessage = "Please enter a valid email address"
                            return@launch
                        }

                        if (password.length < 6) {
                            errorMessage = "Password must be at least 6 characters"
                            return@launch
                        }

                        isLoading = true
                        val success = authService.register(name, email, password, address, phone, selectedRole )
                        isLoading = false

                        if (success) {
                            Toast.makeText(navController.context, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show()

                            // Điều hướng về màn hình đăng nhập
                            navController.navigate("login") {
                                popUpTo("signup") { inclusive = true }
                            }
                        }
                        else {
                            errorMessage = "Registration failed. Please try again."
                            snackbarHostState.showSnackbar("Registration failed. Please try again.")
                        }
                    }
                },
                text = "Sign Up"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 52.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Bạn đã có tài khoản? ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = AlegreyaSansFontFamily,
                    color = Color.White
                )
            )
            Text(
                "Đăng Nhập",
                modifier = Modifier.clickable {
                    navController.navigate("login")
                },
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = AlegreyaSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}


