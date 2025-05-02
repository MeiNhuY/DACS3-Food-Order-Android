package com.example.doancoso3.Activity.Splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.doancoso3.R
import com.example.doancoso3.ui.theme.AlegreyaFontFamily
import com.example.doancoso3.ui.theme.AlegreyaSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.darkBrown))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(350.dp)
                .offset(y = (-20).dp)
                .fillMaxWidth()
        ) {
            val (backgroundImg, logImg, loginText,emailField, passwordField) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.intro_pic),
                contentDescription = "",
                modifier = Modifier
                    .height(900.dp)
                    .padding(top = 0.dp)
                    .offset(x = (4).dp, y = (50).dp)
                    .constrainAs(backgroundImg) {
                        top.linkTo(parent.top) // Đảm bảo ảnh nền vẫn nằm ở trên cùng
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .offset(y = (-80).dp), // Di chuyển ảnh nền lên với offset y
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.pizza),
                contentDescription = "",
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 0.dp) // Giảm hoàn toàn padding
                    .offset(x = (5).dp, y = (-10).dp) // Di chuyển lên mạnh hơn với giá trị y = (-80).dp
                    .constrainAs(logImg) {
                        top.linkTo(backgroundImg.top) // Đảm bảo ảnh pizza nằm ngay trên ảnh nền
                        bottom.linkTo(backgroundImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.Fit
            )



            Text(
                text = "Đăng Ký",
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = AlegreyaFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF), // màu viền đậm (giống mẫu)
                    drawStyle = Stroke(width = 10f) // độ dày viền
                ),
                modifier = Modifier
                    .constrainAs(loginText) {
                        top.linkTo(logImg.bottom, margin = (-50).dp) // Di chuyển xuống dưới ảnh pizza
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Bên trong
            Text(
                text = "Đăng Ký",
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = AlegreyaFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA500) // màu bên trong (xanh sáng)
                ),
                modifier = Modifier
                    .constrainAs(loginText) {
                        top.linkTo(logImg.bottom, margin = (-50).dp) // Di chuyển xuống dưới ảnh pizza
                        start.linkTo(parent.start)
                        end.linkTo(parent.end) // Căn giữa chữ
                    }
            )
        }

        CTextField(hint = "Tên Đăng Nhập", value = "")
        CTextField(hint = "Email", value = "")
        CTextField(hint = "Password", value = "")

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), // <-- Chỉ chiếm đủ chiều cao của CButton
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CButton(text = "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Thêm tí khoảng cách nếu muốn đẹp

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=12.dp, bottom = 52.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // Căn giữa Row
        ){
            Text("Bạn đã có tài khoản? ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = AlegreyaSansFontFamily,
                    color = Color.White
                )
            )
            Text(
                "Đăng Nhập",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = AlegreyaSansFontFamily,
                    fontWeight = FontWeight(800),
                    color = Color.White
                )
            )
        }



    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreen()
}