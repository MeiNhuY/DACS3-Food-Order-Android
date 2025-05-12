package com.example.doancoso3.Activity.DetailEachFood

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.doancoso3.ViewModel.ReviewViewModel


class DetailEachFoodActivity : BaseActivity() {
    private lateinit var item: FoodModel
    private lateinit var managmentCart: ManagmentCart

    // Khởi tạo ReviewViewModel
    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = intent.getSerializableExtra("object") as FoodModel
        item.numberInCart = 1
        managmentCart = ManagmentCart(this)

        setContent {
            DetailScreen(
                item = item,
                onBackClick = { finish() },
                onAddToCartClick = { managmentCart.insertItem(item) },
                viewModel = viewModel // Truyền ViewModel vào Composable
            )
        }
    }
}

@Composable
private fun DetailScreen(
    item: FoodModel,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    viewModel: ReviewViewModel // Nhận ViewModel từ Activity
) {
    var numberInCart by remember { mutableStateOf(item.numberInCart) }
    var isFavorite by remember { mutableStateOf(false) } // trạng thái yêu thích
    var rating by remember { mutableStateOf(0f) }
    var comment by remember { mutableStateOf("") }

    // Lấy reviews từ ViewModel
    val reviews by viewModel.reviewList.observeAsState(emptyList())

    // Lấy reviews từ Firebase khi màn hình được hiển thị
    LaunchedEffect(item.Id) { // Sử dụng item.Id thay vì foodId
        viewModel.loadReviews(item.Id) // Truyền item.Id vào
    }

    ConstraintLayout {
        val (footer, column) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .padding(bottom = 80.dp)
        ) {
            HeaderSection(
                item = item,
                numberInCart = numberInCart,
                isFavorite = isFavorite,
                onBackClick = onBackClick,
                onIncrement = {
                    numberInCart++
                    item.numberInCart = numberInCart
                },
                onDecrement = {
                    if (numberInCart > 1) {
                        numberInCart--
                        item.numberInCart = numberInCart
                    }
                },
                onToggleFavorite = {
                    isFavorite = !isFavorite
                    // Bạn có thể gọi Firebase để lưu trạng thái này nếu cần
                }
            )
            DescriptionSection(item.Description)

            ReviewSection(
                rating = rating,
                comment = comment,
                onRatingChanged = { rating = it },
                onCommentChanged = { comment = it },
                onSendClick = {
                    if (rating > 0 && comment.isNotBlank()) {
                        viewModel.submitReviewWithCurrentUser(item.Id, rating, comment)
                        rating = 0f
                        comment = ""
                    }
                }
            )

            // Hiển thị danh sách đánh giá đã được tải từ Firebase
            SubmittedReviewsSection(reviews = reviews)
        }

        FooterSection(
            onAddToCartClick,
            totalPrice = (item.Price * numberInCart),
            Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        )
    }
}
