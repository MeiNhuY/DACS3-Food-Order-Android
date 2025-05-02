package com.example.doancoso3.Helper

import com.google.firebase.auth.FirebaseAuth

object  LoginHelper {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Đăng nhập thành công
                    onLoginSuccess()
                } else {
                    // Lỗi đăng nhập
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    onLoginFailure(errorMessage)
                }
            }
    }
}