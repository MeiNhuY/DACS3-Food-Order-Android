package com.example.doancoso3.Activity.ItemsList

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.R
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.core.content.ContextCompat.startActivity
import com.example.doancoso3.Activity.DetailEachFood.DetailEachFoodActivity


@Composable
fun ItemsList(items: List<FoodModel>){
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        itemsIndexed(items){
            index, item ->
            Items(item=item, index=index)
        }
    }
}

@Composable
fun Items(item: FoodModel, index: Int) {
    val context= LocalContext.current
    val isEvenRow= index % 2 == 0
    Row  (
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .clickable {
            val intent=Intent(context, DetailEachFoodActivity::class.java).apply {
                putExtra("object",item)
            }
                startActivity(context,intent,null)
            }
    ){
        if (isEvenRow) {
            FoodImage(item=item)
            FoodDetails(item=item)
        }else{
            FoodDetails(item=item)
            FoodImage(item=item)
        }
    }

}


@Composable
fun FoodImage(item: FoodModel) {
    AsyncImage(
        model = item.ImagePath,
        contentDescription =null,
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(R.color.grey),shape = RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun RowScope.FoodDetails(item:FoodModel){
    Column (
        modifier = Modifier
        .padding(start = 8.dp)
        .fillMaxHeight()
        .weight(1f)
    ) {
        Text(
            text=item.Title,
            color= colorResource(R.color.darkPurple),
            fontSize= 16.sp,
            fontWeight=FontWeight.SemiBold,
            maxLines=1 ,
            overflow= TextOverflow.Ellipsis,
            modifier=Modifier.padding(top=8.dp)
        )
        TimingRow(item.TimeValue)
        RatingBarRow(item.Star)
        Text(
            text = "$${item.Price}",
            color = colorResource(R.color.darkPurple),
            fontSize=18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


@Composable
fun TimingRow(timedValue: Int){
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top=8.dp)
        ){
        Image(painter = painterResource(R.drawable.time),
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp )
            )
        Text(text="$timedValue min", style=MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun RatingBarRow(star: Double) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ){
        Image(painter = painterResource(R.drawable.star),
            contentDescription=null,
            modifier=Modifier.padding(end = 8.dp)
        )
        Text(text="$star", style = MaterialTheme.typography.bodyMedium)
    }
}