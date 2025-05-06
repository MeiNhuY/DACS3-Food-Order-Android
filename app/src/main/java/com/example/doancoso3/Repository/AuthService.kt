package com.example.doancoso3.Repository


// Import các lớp cần thiết từ Firebase và Kotlin coroutine
import com.example.doancoso3.Domain.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

// Lớp FirebaseService dùng để xử lý xác thực và lưu trữ người dùng
open class AuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance() // Lấy instance FirebaseAuth
    private val database = FirebaseDatabase.getInstance().getReference("Users") // Tham chiếu đến nhánh "users" trong Firebase Realtime Database

    // Hàm đăng ký người dùng (sử dụng coroutine)
    suspend fun register(name: String, email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await() // Tạo tài khoản với email và password
            val uid = result.user?.uid ?: return false // Lấy UID người dùng, nếu null thì trả false
            val user = UserModel(uid, name, email) // Tạo đối tượng người dùng
            database.child(uid).setValue(user).await() // Lưu vào Firebase Database
            true // Trả về true nếu thành công
        } catch (e: Exception) {
            false // Trả về false nếu có lỗi
        }
    }

    // Hàm đăng nhập người dùng (sử dụng coroutine)
    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await() // Đăng nhập với email/password
            true // Trả về true nếu thành công
        } catch (e: Exception) {
            false // Trả về false nếu có lỗi
        }
    }

    // Đăng nhập bằng Google
    suspend fun signInWithGoogle(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null) // Tạo credential từ idToken
            val result = auth.signInWithCredential(credential).await() // Đăng nhập bằng credential
            val user = result.user ?: return false // Lấy người dùng từ kết quả, nếu null thì return
            val uid = user.uid // Lấy UID người dùng
            val name = user.displayName ?: "No Name" // Lấy tên người dùng (nếu null thì gán "No Name")
            val email = user.email ?: return false // Lấy email người dùng
            val newUser = UserModel(uid, name, email) // Tạo đối tượng người dùng mới
            database.child(uid).setValue(newUser).await() // Lưu người dùng vào database
            true // Trả về true nếu thành công
        } catch (e: Exception) {
            println("Lỗi khi đăng nhập với Google: ${e.message}") // In lỗi nếu có
            false // Trả về false nếu có lỗi
        }
    }

    // Hàm đăng ký người dùng (sử dụng callback thay vì coroutine)
    fun registerUser(email: String, password: String, name: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password) // Tạo tài khoản với email/password
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid // Lấy UID người dùng
                    if (uid == null) {
                        onComplete(false, "Không lấy được UID") // Trả về lỗi nếu không có UID
                        return@addOnCompleteListener
                    }
                    val user = UserModel(uid, name, email) // Tạo đối tượng người dùng
                    database.child(uid).setValue(user) // Lưu người dùng vào database
                        .addOnCompleteListener { dbTask ->
                            onComplete(dbTask.isSuccessful, dbTask.exception?.message) // Gọi callback với kết quả
                        }
                } else {
                    onComplete(false, task.exception?.message) // Trả lỗi nếu đăng ký thất bại
                }
            }
    }

    // Hàm đăng nhập người dùng (sử dụng callback)
    fun loginUser(email: String, password: String, onComplete: (Boolean, String?, UserModel?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password) // Đăng nhập bằng email/password
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid // Lấy UID người dùng
                    if (uid == null) {
                        onComplete(false, "Không có UID", null) // Nếu không có UID thì gọi callback lỗi
                        return@addOnCompleteListener
                    }
                    fetchUser(uid) { user -> // Gọi hàm lấy dữ liệu người dùng từ database
                        if (user != null) {
                            onComplete(true, null, user) // Trả về thành công và user
                        } else {
                            onComplete(false, "Không tìm thấy người dùng", null) // Không tìm thấy user
                        }
                    }
                } else {
                    onComplete(false, task.exception?.message, null) // Trả lỗi nếu đăng nhập thất bại
                }
            }
    }

    // Hàm lấy dữ liệu người dùng từ Firebase Database
    private fun fetchUser(uid: String, onComplete: (UserModel?) -> Unit) {
        database.child(uid).get() // Truy xuất đến UID trong nhánh "users"
            .addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(UserModel::class.java) // Chuyển dữ liệu thành đối tượng UserModel
                onComplete(user) // Trả về user qua callback
            }
            .addOnFailureListener {
                onComplete(null) // Trả null nếu lỗi xảy ra
            }
    }

    // Hàm đăng xuất người dùng
    fun logout() {
        auth.signOut() // Đăng xuất khỏi Firebase
    }
}
