package com.example.doancoso3.Activity.Splash

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.R

@Composable
@Preview
fun GetStartedButton(onclick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Button Sign Up
        Button(
            onClick = onclick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White // màu chữ trắng
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = modifier
                .padding(end = 16.dp)
                .fillMaxWidth(0.35f)
                .border(1.dp, Color.White, shape = RoundedCornerShape(50.dp))
                .height(50.dp)
        ) {
            Text(
                text = "Sign up",
                fontSize = 16.sp
            )
        }

        // Button Get Started
        Button(
            onClick = {onclick()},
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.orange),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Get Started",
                fontSize = 16.sp
            )
        }
    }
}
