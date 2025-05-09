package com.example.doancoso3.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doancoso3.Domain.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val _user = MutableStateFlow(UserModel())
    val user: StateFlow<UserModel> = _user
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Trạng thái xác thực
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    init {
        loadUserData()
    }

    private fun loadUserData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val ref = FirebaseDatabase.getInstance().getReference("Users").child(uid)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                user?.let {
                    _user.value = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


    sealed class AuthState {
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
    }
}

