package com.example.doancoso3.Activity.Cart

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.doancoso3.Activity.Dashboard.BottomMenuItem
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.R
import com.uilover.project2142.Helper.ManagmentCart

@Composable
fun CartItem(
    cartItems: ArrayList<FoodModel>,
    item: FoodModel,
    managmentCart: ManagmentCart,
    onTtemChange: () ->Unit
){
    ConstraintLayout(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .border(1.dp, colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
    ) {
        val (pic, titleTxt, feeEachTime, totalEachItem, quantity)=createRefs()
        var numberInCart by remember { mutableStateOf(item.numberInCart) }
        val decimaFormat= DecimalFormat("#.00")
        Image(
            painter = rememberAsyncImagePainter(item.ImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(135.dp)
                .height(100.dp)
                .background(
                    colorResource(R.color.grey),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(pic){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = item.Title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(titleTxt){
                    start.linkTo(pic.end)
                    top.linkTo(pic.top)

                }
                .padding(start = 8.dp, top = 8.dp)
        )
        Text(text = "$${decimaFormat.format(item.Price)}",
            fontSize = 16.sp,
            color = colorResource(R.color.darkPurple),
            modifier = Modifier
                .constrainAs(feeEachTime){
                    start.linkTo(titleTxt.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }
                .padding(start = 8.dp)

        )
        Text(
            text ="$${decimaFormat.format(numberInCart*item.Price)}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(totalEachItem){
                    end.linkTo(parent.end)
                    bottom.linkTo(pic.bottom)
                }
                .padding(8.dp)

        )
        ConstraintLayout(
            modifier = Modifier
                .width(100.dp)
                .padding(start = 8.dp)
                .constrainAs(quantity){
                    start.linkTo(titleTxt.start)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            val(plusCartBtn, minusCartBtn, numberItemText)=createRefs()
            Text(text=item.numberInCart.toString(),
                color = colorResource(R.color.darkPurple),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(numberItemText){
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
            Box(modifier = Modifier
                .padding(2.dp)
                .size(28.dp)
                .constrainAs(plusCartBtn){
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }.clickable {
                    managmentCart.plusItem(cartItems, cartItems.indexOf(item)){onTtemChange()}
                    numberInCart++
                    item.numberInCart=numberInCart
                }
            ){
                Text(
                    text = "+",
                    color = colorResource(R.color.orange),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier
                .padding(2.dp)
                .size(28.dp)
                .constrainAs(minusCartBtn){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }.clickable {
                    managmentCart.minusItem(cartItems, cartItems.indexOf(item)){onTtemChange()}
                    numberInCart--
                    item.numberInCart=numberInCart
                }
            ){
                Text(
                    text = "-",
                    color = colorResource(R.color.orange),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}