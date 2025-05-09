package com.example.doancoso3.Activity.Dashboard

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.doancoso3.Activity.ItemsList.ItemsListActivity
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.R

 @Composable
fun CategorySection(categories: SnapshotStateList<CategoryModel>, showCategoryLoading: Boolean) {
    Text(
        text = "Choose Category",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
    if (showCategoryLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val rows = categories.chunked(3)
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEachIndexed { index, categoryModel ->
                        CategoryItem(
                            category = categoryModel,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            onItemClick = {
                            val intent= Intent(context,ItemsListActivity::class.java).apply {
                                putExtra("id", categoryModel.id.toString())
                                putExtra("title",categoryModel.name)
                            }
                                startActivity(context, intent, null)
                            }

                        )
                    }
                    if (row.size < 3) {
                        repeat(3 - row.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
        @Composable
        fun CategoryItem(category: CategoryModel, modifier: Modifier=Modifier,onItemClick:()->Unit){
            Column (
                modifier=modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.lightOrange),
                        shape = RoundedCornerShape(13.dp)
                    )
                    .clickable(onClick = onItemClick)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AsyncImage(
                    model = category.imagePath,
                    contentDescription = null,
                    modifier=Modifier.size(80.dp)
                )
                Text(
                    text = category.name,
                    color = colorResource(R.color.darkPurple),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )

            }
        }

