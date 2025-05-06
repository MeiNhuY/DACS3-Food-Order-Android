package com.example.doancoso3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.Domain.FoodModel
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

    // Đăng nhập
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email hoặc mật khẩu không được để trống")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _authState.value = if (task.isSuccessful) {
                    AuthState.Authenticated
                } else {
                    AuthState.Error(task.exception?.message ?: "Đăng nhập thất bại")
                }
            }
    }

    // Đăng ký
    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email hoặc mật khẩu không được để trống")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _authState.value = if (task.isSuccessful) {
                    AuthState.Authenticated
                } else {
                    AuthState.Error(task.exception?.message ?: "Đăng ký thất bại")
                }
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
}

// Trạng thái xác thực
sealed class AuthState {


    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
