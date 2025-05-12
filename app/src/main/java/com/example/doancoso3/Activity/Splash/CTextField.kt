package com.example.doancoso3.Activity.Splash

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.ui.theme.AlegreyaFontFamily

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CTextField(
    onValueChanged: (String) -> Unit = {},
    hint: String,
    value: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = None
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = AlegreyaFontFamily,
                    color = Color(0xFFBEC2C2)
                )
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        visualTransformation = visualTransformation
    )
}
