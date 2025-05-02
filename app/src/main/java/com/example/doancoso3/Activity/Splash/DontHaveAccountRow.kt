package com.example.doancoso3.Activity.Splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.ui.theme.AlegreyaSansFontFamily

@Composable
fun DontHaveAccountRow(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=12.dp, bottom = 52.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // Căn giữa Row
    ){
        Text("Bạn chưa có tài khoản? ",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = AlegreyaSansFontFamily,
                color = Color.White
            )
        )
        Text(
            text = "Đăng Ký",
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable { onSignUpClick() }, // Nhấn vào gọi callback
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = AlegreyaSansFontFamily,
                fontWeight = FontWeight(800),
                color = Color.Yellow // Tô màu nổi bật nếu muốn
            )
        )
    }
}


