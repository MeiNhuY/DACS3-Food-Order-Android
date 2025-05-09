package com.example.doancoso3.Activity.Favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.doancoso3.R

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconRes = if (isFavorite) R.drawable.fav_icon_outline else R.drawable.fav_icon
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = modifier
            .padding(end = 16.dp, top = 48.dp)
            .clickable { onClick() }
            .size(32.dp) // 👈 đảm bảo cả hai icon có cùng kích thước
    )
}
