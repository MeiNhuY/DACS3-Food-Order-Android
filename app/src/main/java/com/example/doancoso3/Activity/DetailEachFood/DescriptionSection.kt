package com.example.doancoso3.Activity.DetailEachFood

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.doancoso3.R


@Composable
fun DescriptionSection(description: String){
Column {
    Text(
        text="Details",
        fontSize=18.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.darkPurple),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = description,
        fontSize = 16.sp,
        color = colorResource(R.color.darkPurple),
        modifier = Modifier.padding(16.dp)
    )
}
}


