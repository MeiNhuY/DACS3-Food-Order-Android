package com.example.doancoso3.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.Domain.FoodModel
import com.example.doancoso3.Domain.OrderModel
import com.example.doancoso3.Repository.MainRepository
import com.google.firebase.auth.FirebaseAuth

// ViewModel chính
class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    // Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Trạng thái xác thực
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState


    var orderDetailState by mutableStateOf<OrderModel?>(null)
        private set

    var foodDetailMap by mutableStateOf<Map<String, FoodModel>>(emptyMap())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun getDetailOrder(orderId: String) {
        isLoading = true
        errorMessage = null

        repository.getDetailOrder(
            orderId,
            onResult = { order, foodMap ->
                orderDetailState = order
                foodDetailMap = foodMap
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }


    init {
        checkAuthStatus()
    }

    // Kiểm tra người dùng đã đăng nhập chưa
    fun checkAuthStatus() {
        _authState.value = if (auth.currentUser == null) {
            AuthState.Unauthenticated
        } else {
            AuthState.Authenticated
        }
    }

    // Đăng xuất
    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    // Tải danh sách banner
    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return repository.loadBanner()
    }

    // Tải danh sách danh mục
    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    // Tải danh sách món ăn theo id danh mục
    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>> {
        return repository.loadFiltered(id)
    }

//Search
    private val _searchResults = MutableLiveData<List<FoodModel>>()
    val searchResults: LiveData<List<FoodModel>> get() = _searchResults

    fun searchFoodByName(query: String) {
        repository.searchFoodByName(query).observeForever {
            _searchResults.value = it
        }
    }


// Trạng thái xác thực
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}














//    fun search(queryText: String) {
//        repository.searchByName(queryText).observeForever { result ->
//            _searchResult.value = result
//        }
//    }
//    private val _searchResults = MutableLiveData<List<Any>>() // List chứa cả CategoryModel và FoodModel
//    val searchResults: LiveData<List<Any>> = _searchResults
//
//    fun searchByName(query: String, foodList: List<FoodModel>, categoryList: List<CategoryModel>) {
//        val result = mutableListOf<Any>()
//
//        // Lọc theo tên món ăn
//        result.addAll(foodList.filter {
//            it.Title.contains(query, ignoreCase = true)
//        })
//
//        // Lọc theo tên danh mục
//        result.addAll(categoryList.filter {
//            it.name.contains(query, ignoreCase = true)
//        })
//
//        _searchResults.value = result
//    }
//
//
//}
}




//    // Đăng nhập
//    fun login(email: String, password: String) {
//        if (email.isEmpty() || password.isEmpty()) {
//            _authState.value = AuthState.Error("Email hoặc mật khẩu không được để trống")
//            return
//        }
//
//        _authState.value = AuthState.Loading
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                _authState.value = if (task.isSuccessful) {
//                    AuthState.Authenticated
//                } else {
//                    AuthState.Error(task.exception?.message ?: "Đăng nhập thất bại")
//                }
//            }
//    }
//
//    // Đăng ký
//    fun signup(email: String, password: String) {
//        if (email.isEmpty() || password.isEmpty()) {
//            _authState.value = AuthState.Error("Email hoặc mật khẩu không được để trống")
//            return
//        }
//
//        _authState.value = AuthState.Loading
//
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                _authState.value = if (task.isSuccessful) {
//                    AuthState.Authenticated
//                } else {
//                    AuthState.Error(task.exception?.message ?: "Đăng ký thất bại")
//                }
//            }
//    }