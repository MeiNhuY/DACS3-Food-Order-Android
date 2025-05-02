package com.example.doancoso3.Activity.Splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.doancoso3.Activity.Dashboard.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            LoginScreen(
                onLoginSuccess = {
                    // Khi login thành công, chuyển sang MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // đóng LoginActivity để người dùng không quay lại bằng nút back

        }
            )
        }
    }
}
