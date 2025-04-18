package com.example.doancoso3.Activity.DetailEachFood

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.doancoso3.Activity.Splash.BaseActivity
import com.example.doancoso3.Domain.FoodModel
import com.uilover.project2142.Helper.ManagmentCart
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class DetailEachFoodActivity : BaseActivity() {
    private lateinit var item: FoodModel
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item =intent.getSerializableExtra("object") as FoodModel
        item.numberInCart=1
        managmentCart=ManagmentCart(this)

        setContent{
            DetailScreen(
                item=item,
                onBackClick={finish()},
                onAddToCartClick={
                    managmentCart.insertItem(item)
                }
            )
        }
    }
}
@Composable
private fun DetailScreen(
    item: FoodModel,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
var numberInCart by remember { mutableStateOf(item.numberInCart) }
    ConstraintLayout {
        val (footer,column)= createRefs()
            Column  (
                modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
                .constrainAs(column){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .padding(bottom = 80.dp)
        ){
                HeaderSection(
                    item=item,
                    numberInCart=numberInCart,
                    onBackClick=onBackClick,
                    onIncrement={
                        numberInCart++
                        item.numberInCart=numberInCart

                    },
                    onDecrement={
                        if(numberInCart>1){
                            numberInCart--
                            item.numberInCart=numberInCart
                        }

                    }
                )
                DescriptionSection(item.Description)
            }
        FooterSection(onAddToCartClick,
            totalPrice = (item.Price*numberInCart),
            Modifier.constrainAs(footer){
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
            )
    }
}


