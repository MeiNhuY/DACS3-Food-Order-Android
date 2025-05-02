package com.example.doancoso3.Activity.Splash

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import com.example.doancoso3.ui.theme.AlegreyaFontFamily
@Composable
fun CButton(
    onClick: () -> Unit = {},
    text: String,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),  // Bo tròn góc của nút
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7C9A92)
        ),
        modifier = Modifier
            .width(250.dp)
            .height(52.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = AlegreyaFontFamily,
                fontWeight = FontWeight(500),
                color = Color.White
            )
        )
    }
}
