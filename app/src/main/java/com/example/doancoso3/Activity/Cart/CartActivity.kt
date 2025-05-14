package com.example.doancoso3.Activity.Cart

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.doancoso3.Activity.Splash.BaseActivity
import com.example.doancoso3.R
import com.uilover.project2142.Helper.ManagmentCart
import java.util.ArrayList



class CartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()  // Tạo navController ở đây

            CartScreen(
                managmentCart = ManagmentCart(this),
                navController = navController,  // Truyền navController vào CartScreen
                onBackClick = { finish() },
                onOrderPlaced = {
                    // Khi đặt hàng thành công, chuyển về trang home
                    navController.navigate("home") {
                        popUpTo("cart") { inclusive = true }
                    }
                }
            )
        }
    }
}


@Composable
fun CartScreen(
    managmentCart: ManagmentCart = ManagmentCart(LocalContext.current),
    navController: NavHostController,
    onBackClick: () -> Unit,
    onOrderPlaced: () -> Unit

){
    val cartItem= remember { mutableStateOf(managmentCart.getListCart() )}
    val tax= remember { mutableStateOf(0.0) }
    calculatorCart(managmentCart, tax)


    LazyColumn (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ){
        item {
            ConstraintLayout(modifier = Modifier.padding(top = 36.dp)) {
                val (backBtn, cartTxt) = createRefs()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(cartTxt) { centerTo(parent) },
                    text = "Your Cart",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Image(
                    painter = painterResource(R.drawable.back_grey),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(backBtn) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .clickable { onBackClick() }
                )
            }
        }
            if(cartItem.value.isEmpty()){
                item{
                    Text(
                        text = "Cart Is Empty",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }else{
                items( cartItem.value){ item->
                    CartItem(
                        cartItems=cartItem.value,
                        item=item,
                        managmentCart=managmentCart,
                        onTtemChange = {
                            calculatorCart(managmentCart, tax)
                            cartItem.value= ArrayList(managmentCart.getListCart())
                        }
                    )
                }
                item{
                    Text(
                        text = "Oder Summary",
                        color = colorResource(R.color.darkPurple),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                item{
                CartSummary(
                    itemTotal = managmentCart.getTotalFee(),
                    tax=tax.value,
                    delivery = 10.0
                )
                }
                item{
                    Text(
                        text = "Information",
                        color = colorResource(R.color.darkPurple),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                item {
                    DeliveryInfoBox(onOrderPlaced = {
                        navController.navigate("home") {
                            popUpTo("cart") { inclusive = true }
                        }
                    })
                }
            }
        }
    }


fun calculatorCart(managmentCart: ManagmentCart, tax:MutableState<Double>){
    val percentTax=0.02
    tax.value= Math.round((managmentCart.getTotalFee() * percentTax) *100) / 100.0

    }

